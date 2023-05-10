package com.studentManagement.model.DTO.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTORequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
