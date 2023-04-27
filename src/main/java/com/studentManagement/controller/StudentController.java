package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.StudentDTOSaveRequest;
import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/school/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<StudentDTOResponse> saveStudent(@RequestBody @Valid StudentDTOSaveRequest studentDTOSaveRequest) {
        return new ResponseEntity<>(studentService.saveStudent(studentDTOSaveRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDTOResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PatchMapping("{studentId}/lessons")
    public ResponseEntity<StudentDTOResponse> addLesson(@RequestBody IdDTORequest lessonsIdList,
                                                        @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.addLessons(lessonsIdList, studentId), HttpStatus.OK);
    }

    @PatchMapping("{studentId}/lessons/{lessonId}")
    public ResponseEntity<StudentDTOResponse> deleteLesson(@PathVariable("lessonId") Long lessonId,
                                                           @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.deleteLesson(lessonId, studentId), HttpStatus.OK);
    }

    @PatchMapping("{studentId}/teacher")
    public ResponseEntity<StudentDTOResponse> addOrUpdateTeacher(@RequestBody IdDTORequest teacherId,
                                                                 @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(studentService.addOrUpdateTeacher(teacherId, studentId), HttpStatus.OK);
    }

}
