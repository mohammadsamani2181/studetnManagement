package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.DTO.response.SchoolDTOResponse;
import com.studentManagement.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/school")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "School", description = "School APIs")
public class SchoolController {
    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(
            method = "PUT", description = "Update School Name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = SchoolDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ConstraintViolationException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "School Information",
            content = @Content(
                    schema = @Schema(implementation = SchoolDTOUpdateRequest.class)
            )
    )
    public ResponseEntity<SchoolDTOResponse> updateSchool(@RequestBody @Valid SchoolDTOUpdateRequest schoolDTOUpdateRequest) {
        return new ResponseEntity<>(schoolService.updateSchool(schoolDTOUpdateRequest), HttpStatus.OK);
    }
}
