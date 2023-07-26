package com.studentManagement.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogObject extends LogObject {
    private final Subject logSubject = Subject.REQUEST;
    private Request information;

    @Override
    public String createLogMessage() {
        return String.format("message: %s, subject: %s, information: [%s]",
                this.getMessage(), this.getLogSubject(), this.createLogInformation());
    }

    @Override
    public String createLogInformation() {
        return String.format("Request Method: %s, Request URL: %s, Request Body: %s",
                this.getInformation().method(), this.getInformation().url(), getRequestBody());
    }

    private String getRequestBody() {
        RequestBody requestBody = information.body();
        String requestBodyString = null;

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            requestBodyString = buffer.readString(StandardCharsets.UTF_8);
        }

        return requestBodyString;

    }
}
