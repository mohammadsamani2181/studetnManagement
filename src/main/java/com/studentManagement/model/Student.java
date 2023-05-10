package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Entity
@Table(name = "studentM_Student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BasicUserInformation {

    @Column(name = "level", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private StudentLevel studentLevel;

    @Column(name = "score", nullable = false)
    private int score = 0;


    @Builder.Default
    @ManyToMany(targetEntity = Lesson.class,
                fetch = FetchType.LAZY)
    @JoinTable(name = "studentM_student_lesson",
            joinColumns = {
                    @JoinColumn(name = "student_id")
                    },
            inverseJoinColumns = {
                    @JoinColumn(name = "lesson_id")
            })
    private Set<Lesson> lessons = new HashSet <>();


    @ManyToOne(targetEntity = Teacher.class,
               fetch = FetchType.LAZY)
    private Teacher teacher;


    @OneToOne(targetEntity = User.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User info;

    @ManyToOne(targetEntity = School.class,
               fetch = FetchType.LAZY)
    private School school;


    public void deleteLesson(Lesson lesson) {
        if (!this.getLessons().contains(lesson)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This Student doesn't have such a this Lesson with the given id!!");
        }
        this.getLessons().remove(lesson);
    }

    private void deleteTeacher() {
        this.teacher = null;
    }
}
