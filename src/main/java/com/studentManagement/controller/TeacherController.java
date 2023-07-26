package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.TeacherDTOSaveRequest;
import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import com.studentManagement.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@Validated
@RestController
@RequestMapping("/school/teachers")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "Teacher", description = "Teacher APIs")
public class TeacherController {
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "POST", description = "Save a Teacher"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully Created!",
                    content = {@Content(schema = @Schema(implementation = TeacherDTOResponse.class), mediaType = "application/json")}),

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
            required = true, description = "Teacher Information",
            content = @Content(
                    schema = @Schema(implementation = TeacherDTOSaveRequest.class)
            )
    )
    public ResponseEntity<TeacherDTOResponse> saveTeacher(@RequestBody @Valid TeacherDTOSaveRequest teacherDTOSaveRequest) {
        return new ResponseEntity<>(teacherService.saveTeacher(teacherDTOSaveRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{teacherId}/lessons")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "add some Lessons for Teacher"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = TeacherDTOResponse.class), mediaType = "application/json")}),


            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Teacher with the given ssoId!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})

    })
    @Parameter(
            name = "teacherId", description = "Teacher Id", in = ParameterIn.PATH,
            required = true, example = "6"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Lesson Id List",
            content = @Content(
                    schema = @Schema(implementation = IdDTORequest.class)
            )
    )
    public ResponseEntity<TeacherDTOResponse> addLessons(@RequestBody IdDTORequest lessonsIdList,
                                                         @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.addLessons(lessonsIdList, teacherId), HttpStatus.OK);
    }

    @PatchMapping("/{teacherId}/lessons/{lessonId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "Delete Lesson of Teacher"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = TeacherDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Teacher with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Lesson with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})

    })
    @Parameters({
            @Parameter(
                    name = "lessonId", description = "Lesson Id", in = ParameterIn.PATH,
                    required = true, example = "3"
            ),
            @Parameter(
                    name = "teacherId", description = "Teacher Id", in = ParameterIn.PATH,
                    required = true, example = "4"
            )
    })
    public ResponseEntity<TeacherDTOResponse> deleteLesson(@PathVariable("lessonId") Long lessonId,
                                                           @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.deleteLesson(lessonId, teacherId), HttpStatus.OK);
    }

    @PatchMapping("/{teacherId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "Update Teacher Information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = TeacherDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Teacher with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameter(
            name = "teacherId", description = "Teacher Id", in = ParameterIn.PATH,
            required = true, example = "6"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Teacher Information",
            content = @Content(
                    schema = @Schema(implementation = TeacherDTOUpdateRequest.class)
            )
    )
    public ResponseEntity<TeacherDTOResponse> updateTeacher(@RequestBody TeacherDTOUpdateRequest teacherDTOUpdateRequest,
                                                            @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.updateTeacher(teacherDTOUpdateRequest, teacherId), HttpStatus.OK);
    }

    @DeleteMapping("{teacherId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "DELETE", description = "Delete Teacher"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameter(
            name = "teacherId", description = "Teacher SsoId", in = ParameterIn.PATH,
            required = true, example = "6"
    )
    public ResponseEntity<String> deleteTeacher( @PathVariable("teacherId") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
