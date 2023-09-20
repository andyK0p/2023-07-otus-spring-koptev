package ru.otus.spring.advancedquiz.service;

import ru.otus.spring.advancedquiz.domain.Question;

import java.util.List;

public interface QuestionReader {

    List<Question> readQuestions();
}
