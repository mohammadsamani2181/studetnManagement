package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.model.Principal;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PrincipalDTOResponseMapper implements Function<Principal, PrincipalDTOResponse> {

    @Override
    public PrincipalDTOResponse apply(Principal principal) {
        return PrincipalDTOResponse.builder()
                .firstName(principal.getFirstName())
                .lastName(principal.getLastName())
                .email(principal.getEmail())
                .build();
    }
}
