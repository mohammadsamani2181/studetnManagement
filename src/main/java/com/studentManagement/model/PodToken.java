package com.studentManagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PodToken {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String expiresIn;
}
