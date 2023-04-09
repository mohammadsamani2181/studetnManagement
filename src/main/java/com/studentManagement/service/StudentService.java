package com.studentManagement.service;

import com.studentManagement.model.Student;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);
    List<Student> getAllStudents();
}
