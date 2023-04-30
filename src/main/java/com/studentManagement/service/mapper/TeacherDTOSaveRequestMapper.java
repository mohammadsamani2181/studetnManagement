package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.request.TeacherDTOSaveRequest;
import com.studentManagement.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherDTOSaveRequestMapper implements Function<TeacherDTOSaveRequest, Teacher> {
    @Override
    public Teacher apply(TeacherDTOSaveRequest teacherDTOSaveRequest) {
        return Teacher.builder()
                .firstName(teacherDTOSaveRequest.getFirstName())
                .lastName(teacherDTOSaveRequest.getLastName())
                .email(teacherDTOSaveRequest.getEmail())
                .build();
    }
}
