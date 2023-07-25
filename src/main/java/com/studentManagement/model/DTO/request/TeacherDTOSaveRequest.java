package com.studentManagement.model.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TeacherSaveRequest")
public class TeacherDTOSaveRequest {
    @NotNull(message = "firstName field cannot be null!!")
    private String firstName;
    @NotNull(message = "lastName field cannot be null!!")
    private String lastName;
    @NotNull(message = "email field cannot be null!!")
    private String email;
    private List<Long> lessonsIdList;
}
