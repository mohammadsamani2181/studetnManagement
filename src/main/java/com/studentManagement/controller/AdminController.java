package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.log.ExceptionLogObject;
import com.studentManagement.log.LogObject;
import com.studentManagement.log.LogUtil;
import com.studentManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "Admin", description = "Admin APIs")
public class AdminController {
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(
            method = "PUT", description = "Get Admin role to a User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no User with the given ssoId!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameter(
            name = "ssoId", description = "User SsoId", in = ParameterIn.HEADER,
            required = true, example = "14785236"
    )
    public ResponseEntity<String> makeAdmin(@RequestParam("ssoId") @NotNull Long ssoId) {
        try {
            userService.makeAdmin(ssoId);
        } catch (ResponseStatusException e) {

            LogObject exceptionLog = ExceptionLogObject.builder()
                    .message("An Error occurred during making User admin")
                    .information(e)
                    .build();
            LogUtil.error(log, exceptionLog);
        }
        return new ResponseEntity<>("Role Successfully Added", HttpStatus.OK);
    }
}
