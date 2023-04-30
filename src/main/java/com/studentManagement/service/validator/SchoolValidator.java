package com.studentManagement.service.validator;

import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.School;
import org.springframework.stereotype.Service;

@Service
public class SchoolValidator {
    public School updateSchool(School oldSchool, SchoolDTOUpdateRequest newSchool) {
        oldSchool.setName(newSchool.getName().trim().isEmpty() ? oldSchool.getName() : newSchool.getName());

        return oldSchool;
    }
}
