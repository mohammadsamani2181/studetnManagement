package com.studentManagement.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTOResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private SchoolDTOResponse schoolDTOResponse;
    private List<LessonDTOResponse> lessonDTOResponse;
}
