package com.studentManagement.model.DTO.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDTOResponse {
    private String firstName;
    private String lastName;
    private String email;
}
