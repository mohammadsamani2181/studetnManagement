package com.studentManagement.model.DTO.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTOResponse {
    private Long id;
    private String name;
}
