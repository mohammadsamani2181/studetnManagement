package com.studentManagement.model.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthenticationRequest")
public class AuthenticationDTORequest {
    @NotNull(message = "authentication.email.is.required")
    private String email;
    @NotNull(message = "Password field cannot be null")
    private String password;
}
