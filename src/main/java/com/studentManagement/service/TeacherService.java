package com.studentManagement.service;

import com.studentManagement.model.DTO.response.TeacherDTOResponse;

public interface TeacherService {
    TeacherDTOResponse saveTeacher(Teacher);
}
