package com.studentManagement.service.impl;

import com.studentManagement.model.DTO.request.LessonDTOSaveRequest;
import com.studentManagement.model.DTO.request.LessonDTOType;
import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.model.Lesson;
import com.studentManagement.repository.LessonRepository;
import com.studentManagement.repository.LessonRepositoryManualTransaction;
import com.studentManagement.service.LessonService;
import com.studentManagement.service.mapper.LessonDTORequestMapper;
import com.studentManagement.service.mapper.LessonDTOResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private LessonRepositoryManualTransaction lessonRepositoryManualTransaction;
    private LessonDTORequestMapper lessonDTORequestMapper;
    private LessonDTOResponseMapper lessonDTOResponseMapper;


    public LessonServiceImpl(LessonRepository lessonRepository,
                             LessonRepositoryManualTransaction lessonRepositoryManualTransaction,
                             LessonDTORequestMapper lessonDTORequestMapper,
                             LessonDTOResponseMapper lessonDTOResponseMapper) {

        this.lessonRepository = lessonRepository;
        this.lessonRepositoryManualTransaction = lessonRepositoryManualTransaction;
        this.lessonDTORequestMapper = lessonDTORequestMapper;
        this.lessonDTOResponseMapper = lessonDTOResponseMapper;
    }

    @Override
    public LessonDTOResponse saveLesson(LessonDTOSaveRequest lessonDTOSaveRequest) {
        Lesson lesson = lessonDTORequestMapper.apply(lessonDTOSaveRequest);
        return lessonDTOResponseMapper.apply(lessonRepository.save(lesson));
    }

    @Override
    public LessonDTOResponse saveLessonUsingManualTransaction(LessonDTOSaveRequest lessonDTOSaveRequest) {
        Lesson lesson = lessonDTORequestMapper.apply(lessonDTOSaveRequest);
        lessonRepositoryManualTransaction.save(lesson);
        return lessonDTOResponseMapper.apply(lesson);
    }

    @Override
    public List<LessonDTOResponse> getLessons(LessonDTOType lessonDTOType) {
        return lessonDTOResponseMapper.applyAll(
                lessonRepositoryManualTransaction.getLessonsByType(lessonDTOType.getLessonType())
                        .stream().collect(Collectors.toSet()));
    }
}
