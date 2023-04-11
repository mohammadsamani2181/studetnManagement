package com.studentManagement.service.impl;

import com.studentManagement.exception.sourceNotFoundException;
import com.studentManagement.model.Student;
import com.studentManagement.model.StudentLevel;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.service.GradeStudent;
import com.studentManagement.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        GradeStudent strategy = null;
        if (student.getStudentLevel() == StudentLevel.GENIUS) {
            strategy = new GradeGeniusStudent();
        }
        else {
            strategy = new GradeWeakStudent();
        }
        student.setScore(strategy.grade());

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
    }

    @Override
    public Student updateStudent(Long id, Student newStudent) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
        existingStudent.setFirstName(newStudent.getFirstName());
        existingStudent.setLastName(newStudent.getLastName());
        existingStudent.setEmail(newStudent.getEmail());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
        studentRepository.delete(existingStudent);
    }
}
