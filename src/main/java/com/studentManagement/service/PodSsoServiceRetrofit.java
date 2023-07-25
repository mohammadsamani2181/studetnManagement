package com.studentManagement.service;

import com.studentManagement.model.PODUserInfo;
import com.studentManagement.model.PODUserTokenInfo;
import retrofit2.Call;
import retrofit2.http.*;

public interface PodSsoServiceRetrofit {
    @FormUrlEncoded
    @POST("/oauth2/token")
    Call<PODUserTokenInfo> getUserToken(
            @Header("Authorization") String auth,
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri
    );


    @GET("/srv/core/nzh/getUserProfile")
    Call<PODUserInfo> getUserInfo(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Header("_token_") String accessToken,
            @Header("_token_issuer_") String tokenIssuer
    );
}
