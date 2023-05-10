package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.PrincipalDTOUpdateRequest;
import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.service.PrincipalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/school/principal")
public class PrincipalController {
    private PrincipalService principalService;

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }


    @PatchMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<PrincipalDTOResponse> updatePrincipal(@RequestBody PrincipalDTOUpdateRequest principalDTOUpdateRequest) {
        return new ResponseEntity<>(principalService.update(principalDTOUpdateRequest), HttpStatus.OK);
    }
}
