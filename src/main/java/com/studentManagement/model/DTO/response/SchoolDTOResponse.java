package com.studentManagement.model.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SchoolResponse")
public class SchoolDTOResponse {
    private Long id;
    private String name;
}
