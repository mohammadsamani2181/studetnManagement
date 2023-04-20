package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studentM_Student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseUser{
    @Column(name = "level", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private StudentLevel studentLevel;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToMany(targetEntity = Lesson.class,
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.PERSIST})

    @JoinTable(name = "studentM_student_lesson",
            joinColumns = {
                    @JoinColumn(name = "student_id")/*,
                    @JoinColumn(name = "student_firstName"),
                    @JoinColumn(name = "student_lastName")*/
                    },
            inverseJoinColumns = {
                    @JoinColumn(name = "lesson_id")/*,
                    @JoinColumn(name = "lesson_subject")*/
            })
    private List<Lesson> lessons = new ArrayList<>();


    @ManyToOne(targetEntity = Teacher.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    private Teacher teacher;

    @ManyToOne(targetEntity = School.class,
               fetch = FetchType.LAZY,
               cascade = CascadeType.PERSIST)
    private School school;


    public Student(String firstName, String lastName, String email, StudentLevel studentLevel) {
        super(firstName, lastName, email);
        this.studentLevel = studentLevel;
    }
}
