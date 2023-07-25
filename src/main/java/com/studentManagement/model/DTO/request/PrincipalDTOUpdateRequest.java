package com.studentManagement.model.DTO.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class PrincipalDTOUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
