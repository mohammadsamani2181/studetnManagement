package com.studentManagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.studentManagement.exception.HttpException;
import com.studentManagement.exception.NotAcceptableException;
import com.studentManagement.log.*;
import com.studentManagement.message.AuthMessage;
import com.studentManagement.model.PODUserInfo;
import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.PODUserTokenInfo;
import com.studentManagement.service.PodSsoService;
import com.studentManagement.service.PodSsoServiceRetrofit;
import com.studentManagement.utils.ServiceUtils;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Slf4j
@Service
public class PodSsoServiceImpl implements PodSsoService {

    @Value("${pod.url.token}")
    private String podTokenUrl;

    @Value("${pod.client.Id}")
    private String podClientId;

    @Value("${pod.client.secret}")
    private String podClientSecret;

    @Value("${pod.responseType}")
    private String podResponseType;

    @Value("${pod.scope}")
    private String podScope;

    @Value("${pod.redirectUri}")
    private String podRedirectUri;

    @Value("${pod.platform.address}")
    private String platformAddress;

    @Value("${pod.login.address}")
    private String podLoginAddress;

    @Value("${pod.grantType}")
    private String grantType;

    @Override
    public String getLoginAddress() {
        return podLoginAddress +
                "?client_id=" + podClientId +
                "&response_type=" + podResponseType +
                "&redirect_uri=" + podRedirectUri +
                "&scope=" + podScope;
    }

    @Override
    public PODUserTokenInfo getUserToken(String refreshToken, String code) {

        try {
            HttpPost request = new HttpPost(podTokenUrl);
//            CloseableHttpClient httpClient = HttpClients.createDefault();
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(WRITE_DATES_AS_TIMESTAMPS)
                    .enable(ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

            ArrayList<NameValuePair> params = new ArrayList<>();

            if (!ServiceUtils.isEmpty(refreshToken)) {
                params.add(new BasicNameValuePair("grant_type", "refresh_token"));
                params.add(new BasicNameValuePair("refresh_token", refreshToken));
            } else {
                params.add(new BasicNameValuePair("grant_type", "authorization_code"));
                params.add(new BasicNameValuePair("code", code));
            }

            params.add(new BasicNameValuePair("redirect_uri", podRedirectUri));
            params.add(new BasicNameValuePair("Client_id", podClientId));
            params.add(new BasicNameValuePair("Client_secret", podClientSecret));

            request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            request.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            HttpResponse response = ServiceUtils.executeClient(request);

            System.out.println(response.getStatusLine().getStatusCode());

            if (response.getEntity() != null && response.getStatusLine().getStatusCode() == 200) {
                String responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                System.out.println(responseStr);

                return mapper.readValue(responseStr, PODUserTokenInfo.class);
            } else if (response.getStatusLine().getStatusCode() == 400 || response.getStatusLine().getStatusCode() == 500) {
                throw new NotAcceptableException("Token Is Expired");
            } else {
                throw new HttpException(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PODUserTokenInfo getUserToken(String code) {
        PODUserTokenInfo podUserTokenInfo = null;

        PodSsoServiceRetrofit podSsoServiceRetrofit = AccountsPodServiceGenerator.createService(PodSsoServiceRetrofit.class);
        Call<PODUserTokenInfo> userTokenInfoCall = podSsoServiceRetrofit.getUserToken(getAuthBasic(), grantType, code, podRedirectUri);

        LogObject requestLog = RequestLogObject.builder()
                .message("Request to get User token from pod")
                .information(userTokenInfoCall.request())
                .build();

        LogUtil.info(log, requestLog);

        try {

            Response<PODUserTokenInfo> response = userTokenInfoCall.execute();
            podUserTokenInfo = response.body();

//            response.

            LogObject responseLog = ResponseLogObject.<PODUserTokenInfo>builder()
                    .message(AuthMessage.getAccessTokenSuccessfully(podUserTokenInfo.getAccessToken()))
                    .information(response)
                    .build();
            LogUtil.info(log, responseLog);

        } catch (IOException e) {
            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during getting User token from Pod Service")
                    .information(e)
                    .build();

            LogUtil.error(log, exceptionLog);
        }

        return podUserTokenInfo;
    }


    @Override
    public PODUserInfoResult getPodUser(String accessToken) {
        PODUserInfo podUserInfo = null;
        PodSsoServiceRetrofit podSsoServiceRetrofit = ApiPodServiceGenerator.createService(PodSsoServiceRetrofit.class);
        Call<PODUserInfo> userInfoCall = podSsoServiceRetrofit.getUserInfo(podClientId, podClientSecret, accessToken, "1");

        LogObject requestLog = RequestLogObject.builder()
                .message("Request to get User information from pod")
                .information(userInfoCall.request())
                .build();

        LogUtil.info(log, requestLog);

        try {
            Response<PODUserInfo> response = userInfoCall.execute();
            podUserInfo = response.body();

            LogObject responseLog = ResponseLogObject.<PODUserInfo>builder()
                    .message("User information Successfully gotten from pod service")
                    .information(response)
                    .build();
            LogUtil.info(log, responseLog);

        } catch (IOException e) {
            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during getting User information from Pod Service")
                    .information(e)
                    .build();
            LogUtil.error(log, exceptionLog);
        }
        return podUserInfo.getResult();
    }


    private String getAuthBasic() {
        return "Basic " + DatatypeConverter.printBase64Binary((podClientId + ":" + podClientSecret).getBytes(StandardCharsets.UTF_8));
    }


    //    @Override
//    public PODUserInfo getPodUser(String accessToken) {
//        try {
//            System.out.println("accessToken = " + accessToken);
//            HttpGet request = new HttpGet(
//                    platformAddress + "/nzh/getUserProfile" +
//                            "?client_id=" + podClientId +
//                            "&client_secret=" + podClientSecret
//            );
////            CloseableHttpClient httpClient = HttpClients.createDefault();
//            ObjectMapper mapper = new ObjectMapper()
//                    .registerModule(new JavaTimeModule())
//                    .disable(FAIL_ON_UNKNOWN_PROPERTIES)
//                    .disable(WRITE_DATES_AS_TIMESTAMPS)
//                    .enable(ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
//
//
//            request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
//            request.setHeader("_token_", accessToken);
//            request.setHeader("_token_issuer_", "1");
//
//            HttpResponse response = ServiceUtils.executeClient(request);
//
//            if (response.getStatusLine().getStatusCode() == 200) {
//                String responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//
//                JSONObject data = new JSONObject(responseStr);
//
//                if (data.get("hasError").toString().equals("false")) {
//                    String result = data.getJSONObject("result").toString();
//
//                    return mapper.readValue(result, PODUserInfo.class);
//
//                } else {
//                    throw new InternalServerException("An Error occurred during getting user information from POD Service!");
//                }
//
//            }
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


}
