package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "level", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private StudentLevel studentLevel;
    @Column(name = "score", nullable = false)
    private int score;
    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
