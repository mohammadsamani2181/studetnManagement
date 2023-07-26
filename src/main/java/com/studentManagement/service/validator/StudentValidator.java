package com.studentManagement.service.validator;

import com.studentManagement.model.Student;
import com.studentManagement.model.StudentLevel;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.service.GradeStudent;
import com.studentManagement.service.impl.GradeGeniusStudent;
import com.studentManagement.service.impl.GradeWeakStudent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentValidator {
    private StudentRepository studentRepository;

    public StudentValidator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void chooseStrategy(Student student) {
        GradeStudent strategy = null;
        if (student.getStudentLevel() == StudentLevel.GENIUS) {
            strategy = new GradeGeniusStudent();
        }
        else if (student.getStudentLevel() == StudentLevel.WEAK){
            strategy = new GradeWeakStudent();
        }
        else {
            strategy = new GradeStudent() {
                @Override
                public int grade() {
                    return GradeStudent.super.grade();
                }
            };
        }

        student.setScore(strategy.grade());
    }

    public Optional<Student> checkAndGetStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }
}
