package com.studentManagement.service;

import com.studentManagement.model.School;
import com.studentManagement.repository.SchoolRepository;

import java.util.List;

public interface SchoolService {
    School getTheSchool();
    School saveOrUpdateSchool(School school);
}
