package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studentM_Teacher")
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class Teacher extends BaseUser{
    @Builder.Default
    @ManyToMany(targetEntity = Lesson.class,
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "studentM_teacher_lesson",
               joinColumns = {
               @JoinColumn(name = "teacher_id")/*,
               @JoinColumn(name = "teacher_firstName"),
               @JoinColumn(name = "teacher_lastName")*/},

               inverseJoinColumns = {
               @JoinColumn(name = "lesson_id")/*,
               @JoinColumn(name = "lesson_subject")*/})
    private List<Lesson> lessons = new ArrayList<>();

    @Builder.Default
    @OneToMany(targetEntity = Student.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id")
//    @JoinColumn(name = "teacher_firstName")
//    @JoinColumn(name = "teacher_lastName")
    private List<Student> students = new ArrayList<>();

    @ManyToOne(targetEntity = School.class,
               fetch = FetchType.LAZY,
               cascade = CascadeType.PERSIST)
    private School school;

    public Teacher(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public Teacher() {
    }
}
