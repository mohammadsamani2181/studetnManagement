package com.studentManagement.model.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StudentResponse")
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
