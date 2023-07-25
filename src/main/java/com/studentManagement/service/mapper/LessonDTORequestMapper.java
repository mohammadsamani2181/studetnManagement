package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.request.LessonDTOSaveRequest;
import com.studentManagement.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LessonDTORequestMapper implements Function<LessonDTOSaveRequest, Lesson> {

    @Override
    public Lesson apply(LessonDTOSaveRequest lessonDTOSaveRequest) {
        return Lesson.builder()
                .type(lessonDTOSaveRequest.getType())
                .subject(lessonDTOSaveRequest.getSubject())
                .build();
    }

}
