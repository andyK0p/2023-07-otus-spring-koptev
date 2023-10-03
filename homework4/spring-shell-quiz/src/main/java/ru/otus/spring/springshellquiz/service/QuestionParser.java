package ru.otus.spring.springshellquiz.service;

import ru.otus.spring.springshellquiz.domain.Question;

public interface QuestionParser {
    Question parseQuestion(String line);
}
