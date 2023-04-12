package com.studentManagement.service;

import com.studentManagement.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student newStudent);
    void deleteStudentById(Long id);
    void printSpecificStudents();
}
