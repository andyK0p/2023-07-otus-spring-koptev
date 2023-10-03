package ru.otus.spring.springshellquiz.service;

import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.domain.Result;
import ru.otus.spring.springshellquiz.domain.User;

public interface QuizEvaluationService {

    boolean evaluateAnswerIsCorrect(int answer, Question question);

    Result evaluateQuizResult(User user, int numOfCorrectAnswers);

    boolean evaluatePassed(int numOfCorrectAnswers);
}
