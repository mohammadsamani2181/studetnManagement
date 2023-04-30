package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Entity
@Table(name = "studentM_Lesson")
@Getter
@Setter
@NoArgsConstructor
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
    private Set<Student> students = new HashSet<>();

    @Builder.Default
    @ManyToMany(targetEntity = Teacher.class,
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.PERSIST},
                mappedBy = "lessons")
    private Set<Teacher> teachers = new HashSet<>();

    public Lesson(LessonType type, LessonSubject subject) {
        this.type = type;
        this.subject = subject;
    }
}
