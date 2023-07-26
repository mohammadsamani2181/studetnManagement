package com.studentManagement.model.DTO.request;

import com.studentManagement.model.StudentLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StudentSaveRequest")
public class StudentDTOSaveRequest {
    @NotNull(message = "firstName field cannot be null!!")
    private String firstName;
    @NotNull(message = "lastName field cannot be null!!")
    private String lastName;
    @NotNull(message = "email field cannot be null!!")
    private String email;
    @NotNull(message = "studentLevel field cannot be null!!")
    private StudentLevel studentLevel;
    private List<Long> lessonsIdList;
    private Long teacherId;
}
