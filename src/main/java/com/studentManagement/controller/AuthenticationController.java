package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.AuthenticationDTORequest;
import com.studentManagement.model.DTO.response.AuthenticationDTOResponse;
import com.studentManagement.service.AuthenticationService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/api_redirect/")
    public void authorizePODSSO(@RequestParam() @NotNull @NotEmpty String code) {

        service.authorizePODSSO(code);

    }// **** inja bayad ye error begiri ghesmati ke dari execute
    // mikone oon ssl estefade nakrdim zaheran bayad estefade konim ***


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDTOResponse> authenticate(@RequestBody AuthenticationDTORequest request) {
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }
}








