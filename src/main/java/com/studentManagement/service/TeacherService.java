package com.studentManagement.service;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.TeacherDTOSaveRequest;
import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import org.springframework.web.server.ResponseStatusException;

public interface TeacherService {
    TeacherDTOResponse saveTeacher(TeacherDTOSaveRequest teacherDTOSaveRequest);
    TeacherDTOResponse addLessons(IdDTORequest lessonsIdList, Long teacherId) throws ResponseStatusException;
    void deleteTeacher(Long teacherId);
    TeacherDTOResponse updateTeacher(TeacherDTOUpdateRequest teacherDTOUpdateRequest, Long teacherId);
    TeacherDTOResponse deleteLesson( Long lessonId, Long teacherId);
}
