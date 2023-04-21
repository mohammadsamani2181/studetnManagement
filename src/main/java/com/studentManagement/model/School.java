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
public class School extends BaseEntity{
    private static School instance;
    @Column(name = "name")
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

    private School() {
    }

    public static School getInstance() {
        if (instance == null) {
            synchronized (School.class) {
                if (instance == null) {
                    instance = new School();
                }
            }
        }
        return instance;
    }
}
