package com.studentManagement.service;

import com.studentManagement.model.DTO.request.LessonDTORequest;
import com.studentManagement.model.DTO.response.LessonDTOResponse;
import org.springframework.stereotype.Service;


public interface LessonService {
    LessonDTOResponse saveLesson(LessonDTORequest lessonDTORequest);
}
