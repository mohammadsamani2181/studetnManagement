package com.studentManagement.model.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TeacherUpdateRequest")
public class TeacherDTOUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
}
