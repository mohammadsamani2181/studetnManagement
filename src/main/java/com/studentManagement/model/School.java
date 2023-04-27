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
@Table(name = "studentM_School")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(targetEntity = Student.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private Set<Student> students = new HashSet<>();


    @Builder.Default
    @OneToMany(targetEntity = Teacher.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private Set<Teacher> teachers = new HashSet<>();

}
