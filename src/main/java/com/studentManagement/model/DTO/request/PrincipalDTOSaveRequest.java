package com.studentManagement.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDTOSaveRequest {
    @NotNull(message = "firstName field cannot be null!!")
    private String firstName;
    @NotNull(message = "lastName field cannot be null!!")
    private String lastName;
    @NotNull(message = "email field cannot be null!!")
    private String email;
    @NotNull(message = "password field cannot be null!!")
    private String password;
}
