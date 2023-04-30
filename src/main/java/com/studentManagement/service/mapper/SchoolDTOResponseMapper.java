package com.studentManagement.service.mapper;

import com.studentManagement.model.DTO.response.SchoolDTOResponse;
import com.studentManagement.model.School;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SchoolDTOResponseMapper implements Function<School, SchoolDTOResponse> {
    @Override
    public SchoolDTOResponse apply(School school) {
        return new SchoolDTOResponse(
                school.getId(),
                school.getName()
        );
    }
}
