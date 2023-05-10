package com.studentManagement.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDTOUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
