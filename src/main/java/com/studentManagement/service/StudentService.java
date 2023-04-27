package com.studentManagement.service;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.StudentDTOSaveRequest;
import com.studentManagement.model.DTO.response.StudentDTOResponse;

import java.util.List;

public interface StudentService {

    StudentDTOResponse saveStudent(StudentDTOSaveRequest studentDTOSaveRequest);
    List<StudentDTOResponse> getAllStudents();
    void printSpecificStudents();
    StudentDTOResponse addLessons(IdDTORequest lessonsIdList, Long studentId);
    StudentDTOResponse deleteLesson(Long lessonId, Long studentId);
    StudentDTOResponse addOrUpdateTeacher(IdDTORequest teacherId, Long studentId);
}
