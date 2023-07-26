package com.studentManagement.model.DTO.request;

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
@Schema(name = "SchoolUpdateRequest")
public class SchoolDTOUpdateRequest {
    @NotNull(message = "name field cannot be null!!")
    private String name;
}
