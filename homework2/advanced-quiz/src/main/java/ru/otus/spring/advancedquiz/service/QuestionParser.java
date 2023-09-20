package ru.otus.spring.advancedquiz.service;

import ru.otus.spring.advancedquiz.domain.Question;

public interface QuestionParser {
    Question parseQuestion(String line);
}
