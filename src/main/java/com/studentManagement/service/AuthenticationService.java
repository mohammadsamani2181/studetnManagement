package com.studentManagement.service;


import com.studentManagement.model.User;
import com.studentManagement.model.DTO.request.AuthenticationDTORequest;
import com.studentManagement.model.DTO.response.AuthenticationDTOResponse;

public interface AuthenticationService {
    AuthenticationDTOResponse authenticate(AuthenticationDTORequest request);

    User get(AuthenticationDTORequest request);

    void authorizePODSSO(String code);
}
