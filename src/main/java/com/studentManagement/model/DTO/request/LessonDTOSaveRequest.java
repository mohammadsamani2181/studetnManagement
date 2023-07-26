package com.studentManagement.model.DTO.request;

import com.studentManagement.model.LessonSubject;
import com.studentManagement.model.LessonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LessonSaveRequest")
public class LessonDTOSaveRequest {
    @Schema(description = "lesson type", example = "DAILY, WEAKLY, UNIT")
    @NotNull(message = "{lesson.type.is.required}")
    private LessonType type;
    @NotNull(message = "{lesson.subject.is.required}")
    private LessonSubject subject;
}
