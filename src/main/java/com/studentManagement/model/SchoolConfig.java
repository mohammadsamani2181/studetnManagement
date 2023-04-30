package com.studentManagement.model;

public class SchoolConfig {
    private static SchoolConfig instance;
    private School school; //

    private SchoolConfig() {
    }

    public School getSchool() {
        return school;
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

    public boolean isSchoolExist(School existingSchool) {
        this.school = existingSchool;

        if (this.school == null) {
            this.school = new School();
            return false;
        }

        return true;
    }

}
