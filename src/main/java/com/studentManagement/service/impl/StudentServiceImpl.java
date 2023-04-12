package com.studentManagement.service.impl;

import com.studentManagement.exception.sourceNotFoundException;
import com.studentManagement.model.Student;
import com.studentManagement.model.StudentLevel;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.service.GradeStudent;
import com.studentManagement.service.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Component
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

    @Override
    @Scheduled(fixedRate = 10000)
    public void printSpecificStudents() {
        List<Student> students = findAllStudents();
        for (Student student : filterList(students)) {
            System.out.println(student);
        }
    }

    public List<Student> findAllStudents() {

        List<Student> students = studentRepository.findAllStudents();
        return students;
    }

    private List<Student> filterList(List<Student> students) {
        return students.stream().filter(e -> e.getFirstName().contains("ali")).toList();
    }

    @PostConstruct
    private void printAllTheStudents() {
        List<Student> students = findAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
