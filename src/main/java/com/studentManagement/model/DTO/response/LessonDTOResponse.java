package com.studentManagement.model.DTO.response;

import com.studentManagement.model.LessonSubject;
import com.studentManagement.model.LessonType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LessonResponse")
public class LessonDTOResponse {
    private Long id;
    private LessonType type;
    private LessonSubject subject;
}
