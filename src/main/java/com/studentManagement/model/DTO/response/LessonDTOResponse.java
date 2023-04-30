package com.studentManagement.model.DTO.response;

import com.studentManagement.model.LessonSubject;
import com.studentManagement.model.LessonType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTOResponse {
    private Long id;
    private LessonType type;
    private LessonSubject subject;
}
