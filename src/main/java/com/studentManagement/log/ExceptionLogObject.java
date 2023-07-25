package com.studentManagement.log;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLogObject extends LogObject {
    @Builder.Default
    private Subject logSubject = Subject.AUTH;
    private Throwable information;

    @Override
    public String createLogMessage() {
        return String.format("message: %s, subject: %s, information: [%s]",
                this.getMessage(), this.getLogSubject(), this.createLogInformation());
    }

    @Override
    public String createLogInformation() {
        return String.format("%s, %s", this.getInformation().getMessage(),
                Arrays.toString(this.getInformation().getStackTrace()));
    }
}
