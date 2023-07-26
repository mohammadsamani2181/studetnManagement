package com.studentManagement.service;

import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.model.Principal;

public interface PrincipalService {
    Principal save(Principal principal);

    //    PrincipalDTOResponse update(PrincipalDTOUpdateRequest principalDTOUpdateRequest);
    PrincipalDTOResponse update(Long userSsoId);
}
