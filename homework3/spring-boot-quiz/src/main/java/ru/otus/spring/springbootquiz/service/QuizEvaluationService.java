package ru.otus.spring.springbootquiz.service;

import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.domain.Result;
import ru.otus.spring.springbootquiz.domain.User;

public interface QuizEvaluationService {

    boolean evaluateAnswerIsCorrect(int answer, Question question);

    Result evaluateQuizResult(User user, int numOfCorrectAnswers);

    boolean evaluatePassed(int numOfCorrectAnswers);
}
