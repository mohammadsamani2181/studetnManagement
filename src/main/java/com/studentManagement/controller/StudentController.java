package com.studentManagement.controller;

import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Student;
import com.studentManagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<StudentDTOResponse> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDTOResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDTOResponse> getStudentById (@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<StudentDTOResponse> updateStudent(@PathVariable(value = "id") Long id, @RequestBody Student newStudent) {
        return new ResponseEntity<>(studentService.updateStudent(id, newStudent), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudentById (@PathVariable(value = "id") Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
