package com.studentManagement.threadLocal;

public class ApplicationThreadLocal {
    public static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void startTransaction(String generatedId) {
        userThreadLocal.set(generatedId);
    }

    public static String getTransactionId() {
        return userThreadLocal.get();
    }

    public static void endTransaction() {
        userThreadLocal.remove();
    }
}
