package ru.otus.spring.springshellquiz.service;

import ru.otus.spring.springshellquiz.domain.Question;

import java.util.List;

public interface QuestionReader {

    List<Question> readQuestions();
}
