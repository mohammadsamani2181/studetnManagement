package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class LessonDTOResponseMapper implements Function<Lesson, LessonDTOResponse> {
    @Override
    public LessonDTOResponse apply(Lesson lesson) {
        return LessonDTOResponse.builder()
                .id(lesson.getId())
                .type(lesson.getType())
                .subject(lesson.getSubject())
                .build();
    }

    public List<LessonDTOResponse> applyAll(Set<Lesson> lessons) {
        if (lessons == null) {
            return null;
        }

        List<LessonDTOResponse> lessonDTOResponseList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDTOResponseList.add(apply(lesson));
        }

        return lessonDTOResponseList;
    }
}
