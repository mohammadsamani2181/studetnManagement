package com.studentManagement.model.DTO.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTOResponse {
    private String token;
}
