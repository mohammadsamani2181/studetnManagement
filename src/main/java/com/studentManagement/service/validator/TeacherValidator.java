package com.studentManagement.service.validator;

import com.studentManagement.model.DTO.request.TeacherDTOUpdateRequest;
import com.studentManagement.model.Teacher;
import com.studentManagement.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class TeacherValidator {
    private TeacherRepository teacherRepository;

    public TeacherValidator(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Optional<Teacher> checkAndGetTeacher(Long teacherId) {
        if (teacherId == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Teacher id is null!");
        }
        return teacherRepository.findById(teacherId);
    }

    public Teacher updateTeacher(Teacher oldTeacher, TeacherDTOUpdateRequest newTeacher)
    {
        oldTeacher.setFirstName(
                newTeacher.getFirstName() == null || newTeacher.getFirstName().trim().isEmpty() ?
                         oldTeacher.getFirstName() : newTeacher.getFirstName());

        oldTeacher.setLastName(
                newTeacher.getLastName() == null || newTeacher.getLastName().trim().isEmpty() ?
                        oldTeacher.getLastName() : newTeacher.getLastName());

        oldTeacher.setEmail(
                newTeacher.getEmail() == null || newTeacher.getEmail().trim().isEmpty() ?
                        oldTeacher.getEmail() : newTeacher.getEmail());

        return oldTeacher;
    }
}
