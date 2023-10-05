package ru.otus.spring.springbootquiz.service;

import ru.otus.spring.springbootquiz.domain.Question;

import java.util.List;

public interface QuestionReader {

    List<Question> readQuestions();
}
