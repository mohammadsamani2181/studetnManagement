package com.studentManagement.service.impl;

import com.studentManagement.model.DTO.request.IdDTORequest;
import com.studentManagement.model.DTO.request.StudentDTOSaveRequest;
import com.studentManagement.model.DTO.response.StudentDTOResponse;
import com.studentManagement.model.Lesson;
import com.studentManagement.model.SchoolConfig;
import com.studentManagement.model.Student;
import com.studentManagement.model.Teacher;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.service.StudentService;

import com.studentManagement.service.mapper.StudentDTOResponseMapper;
import com.studentManagement.service.mapper.StudentDTOSaveRequestMapper;
import com.studentManagement.service.validator.LessonValidator;
import com.studentManagement.service.validator.StudentValidator;
import com.studentManagement.service.validator.TeacherValidator;
import org.springframework.http.HttpStatus;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@EnableScheduling
@Component
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StudentDTOResponseMapper studentDTOResponseMapper;
    private StudentDTOSaveRequestMapper studentDTOSaveRequestMapper;
    private StudentValidator studentValidator;
    private LessonValidator lessonValidator;
    private TeacherValidator teacherValidator;
    private SchoolConfig schoolConfig;

    public StudentServiceImpl(StudentRepository studentRepository, StudentDTOResponseMapper studentDTOResponseMapper, StudentDTOSaveRequestMapper studentDTOSaveRequestMapper, StudentValidator studentValidator, LessonValidator lessonValidator, TeacherValidator teacherValidator) {
        this.studentRepository = studentRepository;
        this.studentDTOResponseMapper = studentDTOResponseMapper;
        this.studentDTOSaveRequestMapper = studentDTOSaveRequestMapper;
        this.studentValidator = studentValidator;
        this.lessonValidator = lessonValidator;
        this.teacherValidator = teacherValidator;
        schoolConfig = SchoolConfig.getInstance();
    }

    @Override
    public StudentDTOResponse saveStudent(StudentDTOSaveRequest studentDTOSaveRequest) {
        Student student = studentDTOSaveRequestMapper.apply(studentDTOSaveRequest);
        studentValidator.chooseStrategy(student);

        IdDTORequest idDTORequestLessons = IdDTORequest.builder().idList(studentDTOSaveRequest.getLessonsIdList()).build();
        Set<Lesson> lessons = lessonValidator.checkAndGetLessons(idDTORequestLessons);

        Teacher teacher = null;
        if (studentDTOSaveRequest.getTeacherId() != null) {
            teacher = teacherValidator.checkAndGetTeacher(studentDTOSaveRequest.getTeacherId()).get();
        }

        student.setSchool(schoolConfig.getSchool());
        student.setLessons(lessons);

        if (teacher != null) {
            student.setTeacher(teacher);
        }

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
//    @Scheduled(fixedRate = 10000)
    public void printSpecificStudents() {
        List<Student> students = findAllStudents();
       filterList(students).forEach(System.out::println);
    }

    @Override
    public StudentDTOResponse addLessons(IdDTORequest lessonsIdList, Long studentId)
            throws ResponseStatusException{

        Optional<Student> student = studentValidator.checkAndGetStudent(studentId);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Student with the given id!");
        }

        Set<Lesson> lessons = lessonValidator.checkAndGetLessons(lessonsIdList);
        if (lessons == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You didn't give any Lessons id.Please enter some ids!");
        }

        student.get().getLessons().addAll(lessons);

        return studentDTOResponseMapper.apply(studentRepository.save(student.get()));

    }

    @Override
    public StudentDTOResponse deleteLesson(Long lessonId, Long studentId) {
        Optional<Student> student = studentValidator.checkAndGetStudent(studentId);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Student with the given id!");
        }


        Optional<Lesson> lesson = lessonValidator.checkAndGetLesson(lessonId);
        if (lesson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Lesson with the given id!");
        }

        student.get().deleteLesson(lesson.get());

        return studentDTOResponseMapper.apply(studentRepository.save(student.get()));
    }

    @Override
    public StudentDTOResponse addOrUpdateTeacher(IdDTORequest teacherId, Long studentId) {
        Optional<Student> student = studentValidator.checkAndGetStudent(studentId);
        if (student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Student with the given id!");
        }

        Optional<Teacher> teacher = teacherValidator.checkAndGetTeacher(teacherId.getId());
        if (teacher.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Teacher with the given id!");
        }

        student.get().setTeacher(teacher.get());

        return studentDTOResponseMapper.apply(studentRepository.save(student.get()));
    }

    private List<Student> findAllStudents() {
        List<Student> students = studentRepository.findAllStudents();
        return students;
    }

    private List<Student> filterList(List<Student> students) {
        return students.stream().filter(e -> e.getFirstName().contains("ali")).toList();
    }
}
