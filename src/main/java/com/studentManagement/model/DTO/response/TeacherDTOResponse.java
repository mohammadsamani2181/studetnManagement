package com.studentManagement.model.DTO.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
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
