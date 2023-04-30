package com.studentManagement.model.DTO.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTOResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int score;
    private TeacherDTOResponse teacherDTOResponse;
    private List<LessonDTOResponse> lessonDTOResponseList;
    private SchoolDTOResponse schoolDTOResponse;
}
