package com.studentManagement.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UnknownLogObject extends LogObject {
    private final Subject logSubject = Subject.UNKNOWN_EXCEPTION;

    @Override
    public String createLogMessage() {
        return String.format("message: %s, subject: %s, information: [%s]",
                this.getMessage(), this.getLogSubject(), this.createLogInformation());
    }

    @Override
    public String createLogInformation() {
        return null;
    }
}
