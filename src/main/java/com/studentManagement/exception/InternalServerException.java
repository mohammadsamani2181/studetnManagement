package com.studentManagement.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String msg) {
        super(ExceptionMessage.INTERNAL_SERVER_ERROR + " " + msg);
    }
}
