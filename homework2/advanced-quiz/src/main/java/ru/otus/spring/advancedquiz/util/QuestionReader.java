package ru.otus.spring.advancedquiz.util;

import ru.otus.spring.advancedquiz.domain.Question;

import java.util.List;

public interface QuestionReader {

    List<Question> readQuestions();
}
