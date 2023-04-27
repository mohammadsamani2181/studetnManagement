package com.studentManagement.controller;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.TeacherDTOSaveRequest;
import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import com.studentManagement.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school/teachers")
@Validated
public class TeacherController {
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherDTOResponse> saveTeacher(@RequestBody @Valid TeacherDTOSaveRequest teacherDTOSaveRequest) {
        return new ResponseEntity<>(teacherService.saveTeacher(teacherDTOSaveRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{teacherId}/lessons")
    public ResponseEntity<TeacherDTOResponse> addLessons(@RequestBody IdDTORequest lessonsIdList,
                                                         @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.addLessons(lessonsIdList, teacherId), HttpStatus.OK);
    }

    @PatchMapping("/{teacherId}/lessons/{lessonId}")
    public ResponseEntity<TeacherDTOResponse> deleteLesson(@PathVariable("lessonId") Long lessonId,
                                                            @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.deleteLesson(lessonId, teacherId), HttpStatus.OK);
    }

    @PatchMapping("/{teacherId}")
    public ResponseEntity<TeacherDTOResponse> updateTeacher(@RequestBody TeacherDTOUpdateRequest teacherDTOUpdateRequest,
                                                           @PathVariable("teacherId") Long teacherId) {

        return new ResponseEntity<>(teacherService.updateTeacher(teacherDTOUpdateRequest, teacherId), HttpStatus.OK);
    }

    @DeleteMapping("{teacherId}")
    public ResponseEntity<String> deleteTeacher( @PathVariable("teacherId") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
}
