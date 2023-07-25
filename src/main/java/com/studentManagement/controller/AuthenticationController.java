package com.studentManagement.controller;

import com.studentManagement.exception.InternalServerException;
import com.studentManagement.exception.NotAcceptableException;
import com.studentManagement.log.ExceptionLogObject;
import com.studentManagement.log.LogObject;
import com.studentManagement.log.LogUtil;
import com.studentManagement.model.DTO.request.AuthenticationDTORequest;
import com.studentManagement.model.DTO.response.AuthenticationDTOResponse;
import com.studentManagement.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthenticationController {

    private final AuthenticationService service;

//    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/login")
    @Operation(
            method = "GET", description = "Get Pod Login Address And Redirect User To authorizePODSSO API"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!"),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))}),
    })
    public void getPodLoginAddress(HttpServletResponse response) {
        try {
            service.getPodLoginAddress(response);

        } catch (InternalServerException e) {
            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during making User admin")
                    .information(e)
                    .build();
            LogUtil.error(log, exceptionLog);
        }
    }

    @Operation(
            method = "GET", description = "Get Access And Refresh Token From Pod SSO"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!"),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = NotAcceptableException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})

    })
    @Parameter(
            name = "code", description = "Returned Code From Pod Login Page", in = ParameterIn.HEADER,
            required = true
    )
    @GetMapping("/redirect")
    public void authorizePODSSO(@RequestParam(value = "code") @NotNull @NotEmpty String code) {


        try {
            service.authorizePODSSO(code);
        } catch (InternalServerException e) {
            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during authorize Pod SSO")
                    .information(e)
                    .build();

            LogUtil.error(log, exceptionLog);
        }

    }// **** inja bayad ye error begiri ghesmati ke dari execute
    // mikone oon ssl estefade nakrdim zaheran bayad estefade konim ***


    @PostMapping("/authenticate")
    @Operation(
            method = "POST", description = "Get JWT Token To User (when you use pod services this api is not usable)",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = AuthenticationDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no User with the given email!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Has Email And Password Of User",
            content = @Content(
                    schema = @Schema(implementation = AuthenticationDTORequest.class)
            )
    )
    public ResponseEntity<AuthenticationDTOResponse> authenticate(@RequestBody @Valid AuthenticationDTORequest request) {
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }
}








