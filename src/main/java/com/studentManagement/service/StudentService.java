package com.studentManagement.service;

import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Student;

import java.util.List;

public interface StudentService {

    StudentDTOResponse saveStudent(Student student);
    List<StudentDTOResponse> getAllStudents();
    StudentDTOResponse getStudentById(Long id);
    StudentDTOResponse updateStudent(Long id, Student newStudent);
    void deleteStudentById(Long id);
    void printSpecificStudents();
}
