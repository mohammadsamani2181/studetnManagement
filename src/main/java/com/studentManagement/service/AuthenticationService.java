package com.studentManagement.service;


import com.studentManagement.model.DTO.request.AuthenticationDTORequest;
import com.studentManagement.model.DTO.response.AuthenticationDTOResponse;
import com.studentManagement.model.PodToken;
import com.studentManagement.model.User;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthenticationDTOResponse authenticate(AuthenticationDTORequest request);

    User get(AuthenticationDTORequest request);

    void authorizePODSSO(String code);

    void getPodLoginAddress(HttpServletResponse response);

    PodToken getNewAccessToken(String refreshToken);
}
