package ru.otus.spring.springbootquiz.service;

public interface LocalizationService {
    String getMessage(String key, Object ...args);
}
