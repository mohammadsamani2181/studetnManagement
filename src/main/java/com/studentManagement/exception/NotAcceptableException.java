package com.studentManagement.exception;

public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException(String msg) {
        super(ExceptionMessage.NOT_ACCEPTABLE_REQUEST + "->" + msg);
    }
}
