package com.studentManagement.service.impl;

import com.studentManagement.model.DTO.request.SchoolDTOUpdateRequest;
import com.studentManagement.model.DTO.response.SchoolDTOResponse;
import com.studentManagement.model.School;
import com.studentManagement.model.SchoolConfig;
import com.studentManagement.repository.SchoolRepository;
import com.studentManagement.service.SchoolService;
import com.studentManagement.service.mapper.SchoolDTOResponseMapper;
import com.studentManagement.service.validator.SchoolValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;
    private SchoolDTOResponseMapper schoolDTOResponseMapper;
    private SchoolValidator schoolValidator;
    private SchoolConfig schoolConfig;

    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolDTOResponseMapper schoolDTOResponseMapper, SchoolValidator schoolValidator) {
        this.schoolRepository = schoolRepository;
        this.schoolDTOResponseMapper = schoolDTOResponseMapper;
        this.schoolValidator = schoolValidator;
        this.schoolConfig = SchoolConfig.getInstance();
    }

    @PostConstruct
    public void checkSchool() {
        if (!schoolConfig.isSchoolExist(schoolRepository.getSchool())) {
            saveSchool(schoolConfig.getSchool());
        }
    }

    private void saveSchool(School school) {
        schoolRepository.save(school);
    }

    @Override
    public SchoolDTOResponse updateSchool(SchoolDTOUpdateRequest schoolDTOUpdateRequest) {

        return schoolDTOResponseMapper.apply(schoolRepository.save(
                schoolValidator.updateSchool(schoolConfig.getSchool(), schoolDTOUpdateRequest)
        ));
    }
}
