package com.studentManagement.model.DTO.response;

import com.studentManagement.model.LessonSubject;
import com.studentManagement.model.LessonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTOResponse {
    private Long id;
    private LessonType type;
    private LessonSubject subject;
}
