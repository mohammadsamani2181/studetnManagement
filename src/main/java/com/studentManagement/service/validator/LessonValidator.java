package com.studentManagement.service.validator;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.Lesson;
import com.studentManagement.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class LessonValidator {
    private LessonRepository lessonRepository;

    public LessonValidator(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Set<Lesson> checkAndGetLessons(IdDTORequest ids) {
        if (ids.getIdList() == null) {
            return null;
        }

        Set<Lesson> lessons = new HashSet<>();
        for (Long id : ids.getIdList()) {
            lessons.add(lessonRepository.findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no Lesson with the given id!")
                    ));
        }

        return lessons;
    }

    public Optional<Lesson> checkAndGetLesson(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }
}
