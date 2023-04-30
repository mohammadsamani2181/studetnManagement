package com.studentManagement.model.DTO.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdDTORequest {
    private List<Long> idList;
    private Long id;
}
