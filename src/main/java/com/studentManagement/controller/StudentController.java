package com.studentManagement.controller;

import com.studentManagement.exception.AccessDeniedException;
import com.studentManagement.exception.InternalServerException;
import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.StudentDTOSaveRequest;
import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.service.StudentService;
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
@RequestMapping("/school/students")
@SecurityRequirement(name = "Pod Token")
@Tag(name = "Student", description = "Student APIs")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "POST", description = "Save A Student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully Created!",
                    content = {@Content(schema = @Schema(implementation = StudentDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Lesson with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})

    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Student Information",
            content = @Content(
                    schema = @Schema(implementation = StudentDTOSaveRequest.class)
            )
    )
    public ResponseEntity<StudentDTOResponse> saveStudent(@RequestBody @Valid StudentDTOSaveRequest studentDTOSaveRequest) {
        return new ResponseEntity<>(studentService.saveStudent(studentDTOSaveRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "GET", description = "Get All The Student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = StudentDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    public List<StudentDTOResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PatchMapping("{studentId}/lessons")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "Add Lessons for The Student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = StudentDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Student with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})

    })
    @Parameter(
            name = "studentId", description = "student id", in = ParameterIn.PATH,
            required = true, example = "4"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Lesson Id List",
            content = @Content(
                    schema = @Schema(implementation = IdDTORequest.class)
            )
    )
    public ResponseEntity<StudentDTOResponse> addLesson(@RequestBody IdDTORequest lessonsIdList,
                                                        @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.addLessons(lessonsIdList, studentId), HttpStatus.OK);
    }

    @PatchMapping("{studentId}/lessons/{lessonId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "Delete Lesson Of User"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = StudentDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Student with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Lesson with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameters({
            @Parameter(
                    name = "lessonId", description = "Lesson Id", in = ParameterIn.PATH,
                    required = true, example = "8"
            ),
            @Parameter(
                    name = "studentId", description = "Student Id", in = ParameterIn.PATH,
                    required = true, example = "2"
            )
    })
    public ResponseEntity<StudentDTOResponse> deleteLesson(@PathVariable("lessonId") Long lessonId,
                                                           @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.deleteLesson(lessonId, studentId), HttpStatus.OK);
    }

    @PatchMapping("{studentId}/teacher/{teacherId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'PRINCIPAL', 'PRINCIPAL_ASSISTANT')")
    @Operation(
            method = "PATCH", description = "Add Or Update Teacher of Student"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully Done!",
                    content = {@Content(schema = @Schema(implementation = StudentDTOResponse.class), mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "401", description = "Not authorized!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "403", description = "Access denied!",
                    content = {@Content(schema = @Schema(implementation = AccessDeniedException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Student with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "404", description = "There is no Teacher with the given Id!",
                    content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),

            @ApiResponse(responseCode = "500", description = "Internal Server Error!",
                    content = {@Content(schema = @Schema(implementation = InternalServerException.class))})
    })
    @Parameters({
            @Parameter(
                    name = "teacherId", description = "Teacher Id", in = ParameterIn.PATH,
                    required = true, example = "2"
            )
            ,
            @Parameter(
                    name = "studentId", description = "Student SsoId", in = ParameterIn.PATH,
                    required = true, example = "6"
            )
    })
    public ResponseEntity<StudentDTOResponse> addOrUpdateTeacher(@PathVariable IdDTORequest teacherId,
                                                                 @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.addOrUpdateTeacher(teacherId, studentId), HttpStatus.OK);
    }

}
