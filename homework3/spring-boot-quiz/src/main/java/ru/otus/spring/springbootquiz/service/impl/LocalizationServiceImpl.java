package ru.otus.spring.springbootquiz.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.springbootquiz.service.LocaleProvider;
import ru.otus.spring.springbootquiz.service.LocalizationService;

@Component
public class LocalizationServiceImpl implements LocalizationService {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    public LocalizationServiceImpl(LocaleProvider localeProvider, MessageSource messageSource) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeProvider.getCurrent());
    }
}
