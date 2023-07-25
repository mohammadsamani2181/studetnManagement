package com.studentManagement.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super(ExceptionMessage.ACCESS_DENIED);
    }
}
