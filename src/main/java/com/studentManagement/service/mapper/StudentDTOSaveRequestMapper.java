package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.request.StudentDTOSaveRequest;
import com.studentManagement.model.Student;
import com.studentManagement.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOSaveRequestMapper implements Function<StudentDTOSaveRequest, Student> {
    @Override
    public Student apply(StudentDTOSaveRequest studentDTOSaveRequest) {
        return Student.builder()
                .firstName(studentDTOSaveRequest.getFirstName())
                .lastName(studentDTOSaveRequest.getLastName())
                .email(studentDTOSaveRequest.getEmail())
                .studentLevel(studentDTOSaveRequest.getStudentLevel())
                .build();
    }
}
