package com.studentManagement.service;

import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.DTO.response.SchoolDTOResponse;

public interface SchoolService {

    SchoolDTOResponse updateSchool(SchoolDTOUpdateRequest schoolDTOUpdateRequest);
}
