package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class StudentDTOResponseMapper implements Function<Student, StudentDTOResponse> {
    @Override
    public StudentDTOResponse apply(Student student) {
        return StudentDTOResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .score(student.getScore())
                .schoolDTOResponse(
                        new SchoolDTOResponseMapper().apply(student.getSchool()))
                .teacherDTOResponse(
                        student.getTeacher() != null ? new TeacherDTOResponseMapper().apply(student.getTeacher()) : null)
                .lessonDTOResponseList(
                        student.getLessons() != null ? new LessonDTOResponseMapper().applyAll(student.getLessons()) : null)
                .build();
    }
}
