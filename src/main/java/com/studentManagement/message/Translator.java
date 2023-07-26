package com.studentManagement.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    static String toLocale(String messageCode, Object[] params) {

        return sendMessage(messageCode, params, LocaleContextHolder.getLocale());
    }

    private static String sendMessage(String messageCode, Object[] params, Locale requestLocale) {

        return messageSource.getMessage(messageCode, params, requestLocale);
    }
}
