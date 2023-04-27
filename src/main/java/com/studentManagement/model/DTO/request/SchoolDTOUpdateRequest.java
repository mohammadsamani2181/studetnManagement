package com.studentManagement.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTOUpdateRequest {
    @NotNull(message = "name field cannot be null!!")
    private String name;
}
