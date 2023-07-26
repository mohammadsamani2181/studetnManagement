package com.studentManagement.model.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "IdRequest")
public class IdDTORequest {
    private List<Long> idList;
    private Long id;
}
