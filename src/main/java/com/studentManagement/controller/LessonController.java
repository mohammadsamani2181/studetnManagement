package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.LessonDTORequest;
import com.studentManagement.model.DTO.response.LessonDTOResponse;
import com.studentManagement.model.Lesson;
import com.studentManagement.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/lessons")
    public ResponseEntity<LessonDTOResponse> saveLesson (@RequestBody LessonDTORequest lessonDTORequest) {
        return new ResponseEntity<>(lessonService.saveLesson(lessonDTORequest), HttpStatus.CREATED);
    }
}
