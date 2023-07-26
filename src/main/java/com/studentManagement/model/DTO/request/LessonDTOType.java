package com.studentManagement.model.DTO.request;


import com.studentManagement.model.LessonType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTOType {
    @NotNull(message = "{lesson.type.is.required}")
    private LessonType lessonType;
}
