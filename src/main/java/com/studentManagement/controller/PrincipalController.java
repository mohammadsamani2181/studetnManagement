package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.model.DTO.response.PrincipalDTOResponse;
import com.studentManagement.service.PrincipalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/school/principal")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "Principal", description = "Principal APIs")
public class PrincipalController {
    private PrincipalService principalService;

    public PrincipalController(PrincipalService principalService) {
        this.principalService = principalService;
    }


    @PutMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(
            method = "PUT", description = "Get Principal role to a User" +
            "(Note: we have only one principal and we just give information of a User to the Principal)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = PrincipalDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no User with the given ssoId!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameter(
            name = "ssoId", description = "User SsoId", in = ParameterIn.HEADER,
            required = true, example = "14785236"
    )
    public ResponseEntity<PrincipalDTOResponse> updatePrincipal(@RequestParam("ssoId") Long userSsoId) {
        return new ResponseEntity<>(principalService.update(userSsoId), HttpStatus.OK);
    }
}
