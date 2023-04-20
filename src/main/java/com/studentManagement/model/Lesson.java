package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studentM_Lesson")
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends BaseEntity{
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonType type;

    @Column(name = "subject", nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonSubject subject;

    @Builder.Default
    @ManyToMany(targetEntity = Student.class,
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.PERSIST},
                mappedBy = "lessons")
    private List<Student> students = new ArrayList<>();

    @Builder.Default
    @ManyToMany(targetEntity = Teacher.class,
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.PERSIST},
                mappedBy = "lessons")
    private List<Teacher> teachers = new ArrayList<>();

    public Lesson(LessonType type, LessonSubject subject) {
        this.type = type;
        this.subject = subject;
    }

    public Lesson() {
    }
}
