package com.studentManagement.log;

import org.slf4j.Logger;

public class LogUtil {
    private LogUtil() {
    }

    /* request logs */



    /* response logs */



    /* Auth and Exception logs */


    public static void info(Logger logger, String message, Subject subject) {
        if (logger != null) {
            logger.info(createLoggerMessage(message, subject));
        }
    }


    private static String createLoggerMessage(String message, Subject subject) {
        StringBuilder result = new StringBuilder();

        if (message != null) {
            result.append(message);
        }

        result.append(" subject: ").append(subject.name());


        return result.toString();
    }


    public static void info(Logger logger, LogObject logObject) {
        if (logger != null && logObject != null) {
            logger.info(logObject.createLogMessage());
        }
    }

    public static void debug(Logger logger, LogObject logObject) {
        if (logger != null && logObject != null) {
            logger.debug(logObject.createLogMessage());
        }
    }

    public static void error(Logger logger, LogObject logObject) {
        if (logger != null && logObject != null) {
            logger.error(logObject.createLogMessage());
        }
    }

    public static void fatal(Logger logger, LogObject logObject) {
        if (logger != null && logObject != null) {
            logger.error(logObject.createLogMessage());
        }
    }
}
