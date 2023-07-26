package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.model.DTO.request.LessonDTOSaveRequest;
import com.studentManagement.model.DTO.request.LessonDTOType;
import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.service.LessonService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Validated
@RestController
@RequestMapping("/school/lessons")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "Lesson", description = "Lesson APIs")
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "POST", description = "Save A Lesson"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully Created!",
                    content = {@Content(schema = @Schema(implementation = LessonDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ConstraintViolationException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Lesson Information",
            content = @Content(
                    schema = @Schema(implementation = LessonDTOSaveRequest.class)
            )
    )
    public ResponseEntity<LessonDTOResponse> saveLesson(@RequestBody @Valid LessonDTOSaveRequest lessonDTOSaveRequest) {
        return new ResponseEntity<>(lessonService.saveLesson(lessonDTOSaveRequest), HttpStatus.CREATED);
    }

    @PostMapping("/transaction")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    public ResponseEntity<LessonDTOResponse> saveLessonUsingTransaction(@RequestBody @Valid LessonDTOSaveRequest lessonDTOSaveRequest) {
        return new ResponseEntity<>(lessonService.saveLessonUsingManualTransaction(lessonDTOSaveRequest), HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    public ResponseEntity<List<LessonDTOResponse>> getLessonsByTypeUsingTransaction(@RequestBody @Valid LessonDTOType lessonDTOType) {
        return new ResponseEntity<>(lessonService.getLessons(lessonDTOType), HttpStatus.OK);
    }
}
