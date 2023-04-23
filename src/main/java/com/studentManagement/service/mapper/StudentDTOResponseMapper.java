package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class StudentDTOResponseMapper implements Function<Student, StudentDTOResponse> {
    @Override
    public StudentDTOResponse apply(Student student) {
        return new StudentDTOResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getScore(),
                new TeacherDTOResponseMapper().apply(student.getTeacher()),
                new LessonDTOResponseMapper().applyAll(student.getLessons()),
                new SchoolDTOResponseMapper().apply(student.getSchool())
        );
    }
}
