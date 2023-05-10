package com.studentManagement.model.DTO.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTORequest {
  private String email;
  private String password;
}
