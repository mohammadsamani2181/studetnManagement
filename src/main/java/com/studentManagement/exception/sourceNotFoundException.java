package com.studentManagement.exception;

public class sourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public sourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName + "not found with " + fieldName + ":" + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
