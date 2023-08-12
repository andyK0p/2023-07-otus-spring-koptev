package ru.otus.spring.simplequiz.dao;

import ru.otus.spring.simplequiz.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    void loadQuestions();

    List<Question> getQuestions();

    void importQuestions(List<Question> questions);

    Optional<Question> getQuestionById(int id);
}
