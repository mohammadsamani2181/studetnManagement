package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Entity
@Table(name = "studentM_Teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends BasicUserInformation{
    @Builder.Default
    @ManyToMany(targetEntity = Lesson.class,
                fetch = FetchType.LAZY)
    @JoinTable(name = "studentM_teacher_lesson",
               joinColumns = {
               @JoinColumn(name = "teacher_id")},

               inverseJoinColumns = {
               @JoinColumn(name = "lesson_id")})
    private Set<Lesson> lessons = new HashSet<>();

    @Builder.Default
    @OneToMany(targetEntity = Student.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "teacher_id")
    private Set<Student> students = new HashSet<>();


    @OneToOne(targetEntity = User.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User info;

    @ManyToOne(targetEntity = School.class,
            fetch = FetchType.LAZY)
    private School school;


    public void deleteLesson(Lesson lesson) {
        this.getLessons().remove(lesson);
    }
}
