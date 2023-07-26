package com.studentManagement.exception;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpException extends RuntimeException {
    public HttpException(HttpResponse response) {
        super(getResponseString(response));
    }

    private static String getResponseString(HttpResponse response) {
        String res = "status code: " + response.getStatusLine().getStatusCode() + "; ";
        try {
            if (response.getEntity() == null)
                res += "response entity is null.";
            else
                res += EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            res += "response entity is already read(stream closed).";
        }
        return res;
    }
}
