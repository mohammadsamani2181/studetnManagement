package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.request.LessonDTORequest;
import com.studentManagement.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LessonDTORequestMapper implements Function<LessonDTORequest, Lesson> {

    @Override
    public Lesson apply(LessonDTORequest lessonDTORequest) {
        return new Lesson(
                lessonDTORequest.getType(),
                lessonDTORequest.getSubject()
        );
    }
}
