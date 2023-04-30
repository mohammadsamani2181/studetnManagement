package com.studentManagement.exception;

import java.util.function.Supplier;

public class SourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public SourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName + "not found with " + fieldName + ":" + fieldValue);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
