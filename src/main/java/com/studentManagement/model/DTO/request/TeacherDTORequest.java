package com.studentManagement.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTORequest {
    private String firstName;
    private String lastName;
    private String email;
    private List<LessonDTORequest> lessonDTORequestList;
}
