package ru.otus.spring.advancedquiz.service;

import ru.otus.spring.advancedquiz.domain.Result;

import java.util.List;

public interface QuizIOService {

    void printWelcomeMessage();

    void printQuestion(String question);

    void printAllQuestionsWithAnswers(List<String> questions);

    void printQuizResult(Result result);

    void printQuizError(String message);

    void printStartedMessage();

    void printFinishedMessage();

    void askRepeatQuiz();

    void askShowCorrectAnswers();

    String readUserName();

    int readAnswer();

    String readInput();
}
