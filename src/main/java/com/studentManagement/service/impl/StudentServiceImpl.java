package com.studentManagement.service.impl;

import com.studentManagement.exception.sourceNotFoundException;
import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Student;
import com.studentManagement.model.StudentLevel;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.service.GradeStudent;
import com.studentManagement.service.StudentService;
import com.studentManagement.service.mapper.StudentDTOResponseMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@Component
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudentDTOResponseMapper studentDTOResponseMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentDTOResponseMapper studentDTOResponseMapper) {
        this.studentRepository = studentRepository;
        this.studentDTOResponseMapper = studentDTOResponseMapper;
    }

    @Override
    public StudentDTOResponse saveStudent(Student student) {
        chooseStrategy(student);

        return studentDTOResponseMapper.apply(studentRepository.save(student));
    }

    @Override
    public List<StudentDTOResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentDTOResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTOResponse getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentDTOResponseMapper)
                .orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
    }


    //*********** question: is it better to call another method? **********
    @Override
    public StudentDTOResponse updateStudent(Long id, Student newStudent) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
        chooseStrategy(newStudent);

        existingStudent.setFirstName(newStudent.getFirstName());
        existingStudent.setLastName(newStudent.getLastName());
        existingStudent.setEmail(newStudent.getEmail());
        existingStudent.setScore(newStudent.getScore());

        return studentDTOResponseMapper.apply(studentRepository.save(existingStudent));
    }

    @Override
    public void deleteStudentById(Long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new sourceNotFoundException("Student", "Id", id)
        );
        studentRepository.delete(existingStudent);
    }

    @Override
//    @Scheduled(fixedRate = 10000)
    public void printSpecificStudents() {
        List<Student> students = findAllStudents();
        for (Student student : filterList(students)) {
            System.out.println(student);
        }
    }

    private List<Student> findAllStudents() {
        List<Student> students = studentRepository.findAllStudents();
        return students;
    }

    private List<Student> filterList(List<Student> students) {
        return students.stream().filter(e -> e.getFirstName().contains("ali")).toList();
    }

    private void chooseStrategy(Student student) {
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
}
