package com.studentManagement.utils;

import com.studentManagement.model.SchoolRole;
import com.studentManagement.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ServiceUtils {

    private ServiceUtils() {
    }


    public static HttpResponse executeClient(HttpUriRequest request) throws IOException {
        HttpClient httpclient = createAcceptSelfSignedCertificateClient();
        return httpclient.execute(request);
    }

    public static CloseableHttpClient createAcceptSelfSignedCertificateClient() {

        try {
            SSLContext sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();

            HostnameVerifier allowAllHosts = new NoopHostnameVerifier();


            SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

            return HttpClients
                    .custom()
                    .setSSLSocketFactory(connectionFactory)
                    .build();
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public static boolean isEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    public static void makeAdmin(User user) {
        Set<SchoolRole> authorities = new HashSet<>();
        authorities.add(SchoolRole.ADMIN);
        authorities.add(SchoolRole.PRINCIPAL);
        authorities.add(SchoolRole.PRINCIPAL_ASSISTANT);
        authorities.add(SchoolRole.TEACHER);
        authorities.add(SchoolRole.STUDENT);

        user.getRoles().addAll(authorities);
    }

    public static void makePrincipal(User user) {
        Set<SchoolRole> authorities = new HashSet<>();
        authorities.add(SchoolRole.PRINCIPAL);
        authorities.add(SchoolRole.PRINCIPAL_ASSISTANT);
        authorities.add(SchoolRole.TEACHER);
        authorities.add(SchoolRole.STUDENT);

        user.getRoles().addAll(authorities);
    }
}
