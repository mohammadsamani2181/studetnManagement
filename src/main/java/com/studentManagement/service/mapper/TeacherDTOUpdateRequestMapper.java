package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherDTOUpdateRequestMapper implements Function<TeacherDTOUpdateRequest, Teacher> {
    @Override
    public Teacher apply(TeacherDTOUpdateRequest teacherDTOUpdateRequest) {
        return Teacher.builder()
                .firstName(teacherDTOUpdateRequest.getFirstName())
                .lastName(teacherDTOUpdateRequest.getLastName())
                .email(teacherDTOUpdateRequest.getEmail())
                .build();
    }
}
