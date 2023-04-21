package com.studentManagement.service.impl;

import com.studentManagement.model.School;
import com.studentManagement.repository.SchoolRepository;
import com.studentManagement.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School getTheSchool() {
        return schoolRepository.getTheSchool();
    }

    @Override
    public School saveOrUpdateSchool(School school) {
        return schoolRepository.save(school);
    }
}
