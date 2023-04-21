package com.studentManagement.model;

import com.studentManagement.model.School;
import com.studentManagement.service.SchoolService;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SchoolConfig {
    private School instance;
    private final SchoolService schoolService;

    @Bean
    @PostConstruct
    public School getTheSchool() {
        instance = schoolService.getTheSchool();

        if (instance == null) {
            instance = School.getInstance();
            schoolService.saveOrUpdateSchool(instance);
        }

        return instance;
    }
}
