package com.studentManagement.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String msg) {
        super(ExceptionMessage.INVALID_REQUEST + "->" + msg);
    }
}
