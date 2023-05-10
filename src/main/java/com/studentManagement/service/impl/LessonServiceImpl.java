package com.studentManagement.service.impl;

import com.studentManagement.model.DTO.request.LessonDTORequest;
import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.model.Lesson;
import com.studentManagement.repository.LessonRepository;
import com.studentManagement.service.LessonService;
import com.studentManagement.service.mapper.LessonDTORequestMapper;
import com.studentManagement.service.mapper.LessonDTOResponseMapper;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private LessonDTORequestMapper lessonDTORequestMapper;
    private LessonDTOResponseMapper lessonDTOResponseMapper;


    public LessonServiceImpl(LessonRepository lessonRepository,
                             LessonDTORequestMapper lessonDTORequestMapper,
                             LessonDTOResponseMapper lessonDTOResponseMapper) {

        this.lessonRepository = lessonRepository;
        this.lessonDTORequestMapper = lessonDTORequestMapper;
        this.lessonDTOResponseMapper = lessonDTOResponseMapper;
    }

    @Override
    public LessonDTOResponse saveLesson(LessonDTORequest lessonDTORequest) {
        Lesson lesson = lessonDTORequestMapper.apply(lessonDTORequest);
        return lessonDTOResponseMapper.apply(lessonRepository.save(lesson));
    }
}
