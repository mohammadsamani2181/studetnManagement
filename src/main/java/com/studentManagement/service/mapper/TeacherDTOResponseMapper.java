package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import com.studentManagement.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherDTOResponseMapper implements Function<Teacher, TeacherDTOResponse> {
    @Override
    public TeacherDTOResponse apply(Teacher teacher) {
        return new TeacherDTOResponse(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                new SchoolDTOResponseMapper().apply(teacher.getSchool()),
                new LessonDTOResponseMapper().applyAll(teacher.getLessons())
        );
    }
}
