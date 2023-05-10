package com.studentManagement.config;

import com.studentManagement.model.School;
import com.studentManagement.repository.SchoolRepository;

public class SchoolConfig {
    private static SchoolConfig instance;
    private School school;
    private SchoolRepository schoolRepository;

    private SchoolConfig() {
    }


    public void setSchoolRepository(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }


    public static SchoolConfig getInstance() {
        if (instance == null) {
            synchronized (School.class) {
                if (instance == null) {
                    instance = new SchoolConfig();
                }
            }
        }
        return instance;
    }


    public School getSchool() {
        School existingSchool = schoolRepository.getSchool();

        if (existingSchool == null) {
            School school = new School();
            return schoolRepository.save(school);
        }

        return existingSchool;
    }

}
