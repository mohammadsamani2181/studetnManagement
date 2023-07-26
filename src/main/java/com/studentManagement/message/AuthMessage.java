package com.studentManagement.message;

public class AuthMessage {
    private AuthMessage() {
    }

    public static String getAccessTokenSuccessfully(String accessToken) {
        return Translator.toLocale("authentication.pod.getAccessToken", new Object[]{accessToken});
    }
}
