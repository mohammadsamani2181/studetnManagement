package com.studentManagement.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import retrofit2.Response;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLogObject<T> extends LogObject {
    private final Subject logSubject = Subject.RESPONSE;
    private Response<T> information;

    @Override
    public String createLogMessage() {
        return String.format("message: %s, subject: %s, information: [%s]",
                this.getMessage(), this.getLogSubject(), this.createLogInformation());
    }

    @Override
    public String createLogInformation() {
        return String.format("Response code: %d, Response body: %s ",
                this.getInformation().code(), this.getInformation().body());
    }
}
