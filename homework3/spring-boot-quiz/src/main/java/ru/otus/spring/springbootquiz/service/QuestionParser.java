package ru.otus.spring.springbootquiz.service;

import ru.otus.spring.springbootquiz.domain.Question;

public interface QuestionParser {
    Question parseQuestion(String line);
}
