package com.studentManagement.message;

public class ErrorMessage {
    private ErrorMessage() {
    }

    public static String unknownError() {
        return Translator.toLocale("error.unknown", null);
    }
}
