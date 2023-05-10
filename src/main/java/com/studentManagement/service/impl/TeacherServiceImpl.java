package com.studentManagement.service.impl;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.TeacherDTOSaveRequest;
import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.DTO.response.TeacherDTOResponse;
import com.studentManagement.model.Lesson;
import com.studentManagement.config.SchoolConfig;
import com.studentManagement.model.Teacher;
import com.studentManagement.repository.TeacherRepository;
import com.studentManagement.service.TeacherService;
import com.studentManagement.service.mapper.TeacherDTOResponseMapper;
import com.studentManagement.service.mapper.TeacherDTOSaveRequestMapper;
import com.studentManagement.service.validator.LessonValidator;
import com.studentManagement.service.validator.TeacherValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private TeacherDTOResponseMapper teacherDTOResponseMapper;
    private TeacherDTOSaveRequestMapper teacherDTOSaveRequestMapper;
    private SchoolConfig schoolConfig;
    private LessonValidator lessonValidator;
    private TeacherValidator teacherValidator;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TeacherDTOResponseMapper teacherDTOResponseMapper,
                              TeacherDTOSaveRequestMapper teacherDTOSaveRequestMapper,
                              LessonValidator lessonValidator, TeacherValidator teacherValidator) {

        this.teacherRepository = teacherRepository;
        this.teacherDTOResponseMapper = teacherDTOResponseMapper;
        this.teacherDTOSaveRequestMapper = teacherDTOSaveRequestMapper;
        this.lessonValidator = lessonValidator;
        this.teacherValidator = teacherValidator;
        this.schoolConfig = SchoolConfig.getInstance();
    }

    @Override
    public TeacherDTOResponse saveTeacher(TeacherDTOSaveRequest teacherDTOSaveRequest) {
        IdDTORequest idDTORequest = IdDTORequest.builder().idList(teacherDTOSaveRequest.getLessonsIdList()).build();
        Set<Lesson> lessons = lessonValidator.checkAndGetLessons(idDTORequest);

        Teacher teacher = teacherDTOSaveRequestMapper.apply(teacherDTOSaveRequest);

        teacher.setSchool(schoolConfig.getSchool());
        teacher.setLessons(lessons);

        return teacherDTOResponseMapper.apply(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTOResponse addLessons(IdDTORequest lessonsIdList, Long teacherId)
            throws ResponseStatusException {

        Set<Lesson> lessons = lessonValidator.checkAndGetLessons(lessonsIdList);
        if (lessons == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You didn't give any lessons id.Please enter some ids!");
        }

        Optional<Teacher> teacher = teacherValidator.checkAndGetTeacher(teacherId);
        if (teacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Teacher with the given id!");
        }

        teacher.get().getLessons().addAll(lessons);

        return teacherDTOResponseMapper.apply(teacherRepository.save(teacher.get()));
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        Optional<Teacher> teacher = teacherValidator.checkAndGetTeacher(teacherId);
        if (teacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Teacher with the given id!");
        }

        teacherRepository.deleteById(teacherId);
    }

    @Override
    public TeacherDTOResponse updateTeacher(TeacherDTOUpdateRequest teacherDTOUpdateRequest, Long teacherId)
            throws ResponseStatusException {

        Optional<Teacher> oldTeacher = teacherValidator.checkAndGetTeacher(teacherId);

        if (oldTeacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Teacher with the given id!");
        }

        return teacherDTOResponseMapper.apply(teacherRepository.save(
                teacherValidator.updateTeacher(oldTeacher.get(), teacherDTOUpdateRequest)));
    }

    @Override
    public TeacherDTOResponse deleteLesson(Long lessonId, Long teacherId)
            throws ResponseStatusException{

        Optional<Lesson> lesson = lessonValidator.checkAndGetLesson(lessonId);
        if (lesson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Lesson with the given id!");
        }

        Optional<Teacher> teacher = teacherValidator.checkAndGetTeacher(teacherId);
        if (teacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Teacher with the given id!");
        }

        teacher.get().deleteLesson(lesson.get());

        return teacherDTOResponseMapper.apply(teacherRepository.save(teacher.get()));
    }


}
