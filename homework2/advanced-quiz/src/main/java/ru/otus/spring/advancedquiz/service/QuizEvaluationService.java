package ru.otus.spring.advancedquiz.service;

import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.domain.User;

public interface QuizEvaluationService {

    boolean evaluateAnswerIsCorrect(int answer, Question question);

    Result evaluateQuizResult(User user, int numOfCorrectAnswers);

    boolean evaluatePassed(int numOfCorrectAnswers);
}
