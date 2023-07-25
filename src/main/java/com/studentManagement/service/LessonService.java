package com.studentManagement.service;

import com.studentManagement.model.DTO.request.LessonDTOSaveRequest;
import com.studentManagement.model.DTO.request.LessonDTOType;
import com.studentManagement.model.DTO.response.LessonDTOResponse;

import java.util.List;


public interface LessonService {
    LessonDTOResponse saveLesson(LessonDTOSaveRequest lessonDTOSaveRequest);

    LessonDTOResponse saveLessonUsingManualTransaction(LessonDTOSaveRequest lessonDTOSaveRequest);

    List<LessonDTOResponse> getLessons(LessonDTOType lessonDTOType);
}
