package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class LessonDTOResponseMapper implements Function<Lesson, LessonDTOResponse> {
    @Override
    public LessonDTOResponse apply(Lesson lesson) {
        return new LessonDTOResponse(
             lesson.getId(),
             lesson.getType(),
             lesson.getSubject()
        );
    }

    public List<LessonDTOResponse> applyAll(List<Lesson> lessons) {
        List<LessonDTOResponse> lessonDTOResponseList = new ArrayList<>();

        for (Lesson lesson : lessons) {
            lessonDTOResponseList.add(apply(lesson));
        }

        return lessonDTOResponseList;
    }
}
