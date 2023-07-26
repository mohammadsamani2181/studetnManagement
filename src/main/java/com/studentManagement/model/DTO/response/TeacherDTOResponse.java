package com.studentManagement.model.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TeacherResponse")
public class TeacherDTOResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private SchoolDTOResponse schoolDTOResponse;
    private List<LessonDTOResponse> lessonDTOResponse;
}
