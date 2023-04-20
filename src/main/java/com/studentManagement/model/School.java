package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studentM_School")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class School extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Student.class,
               fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
//    @JoinColumn(name = "school_name")
    List<Student> students = new ArrayList<>();


    @OneToMany(targetEntity = Teacher.class,
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
//    @JoinColumn(name = "school_name")
    List<Teacher> teachers = new ArrayList<>();

    public School(String name) {
        this.name = name;
    }
}
