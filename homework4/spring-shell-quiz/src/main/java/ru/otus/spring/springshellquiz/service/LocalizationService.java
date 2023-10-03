package ru.otus.spring.springshellquiz.service;

public interface LocalizationService {
    String getMessage(String key, Object ...args);
}
