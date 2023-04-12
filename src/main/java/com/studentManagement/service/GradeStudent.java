package com.studentManagement.service;

public interface GradeStudent {
    default int grade() {
        return 15;
    };
}
