package com.studentManagement.log;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogObject extends LogObject {
    private final Subject logSubject = Subject.NORMAL_INFO;
    private String firstUserSsoId;
    private String secondUserSsoId;

    @Override
    public String createLogMessage() {
        return String.format("message: %s, subject: %s, information: [%s]",
                this.getMessage(), this.getLogSubject(), this.createLogInformation());
    }

    @Override
    public String createLogInformation() {
        return String.format("User with SsoId: (%s) wanted to * %s * User with SsoId: (%s), and Successfully done!",
                this.getFirstUserSsoId(), this.getMessage(), this.getSecondUserSsoId());
    }
}
