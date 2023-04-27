package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import com.studentManagement.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherDTOResponseMapper implements Function<Teacher, TeacherDTOResponse> {
    @Override
    public TeacherDTOResponse apply(Teacher teacher) {
        return TeacherDTOResponse.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .schoolDTOResponse(new SchoolDTOResponseMapper()
                .apply(teacher.getSchool()))
                .lessonDTOResponse(new LessonDTOResponseMapper()
                .applyAll(teacher.getLessons()))
                .build();
    }
}
