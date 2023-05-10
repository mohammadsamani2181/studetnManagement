package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studentM_Principal")
public class Principal extends BasicUserInformation {

    @OneToOne(targetEntity = School.class,
            fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private School school;

    @OneToOne(targetEntity = User.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User info;
}
