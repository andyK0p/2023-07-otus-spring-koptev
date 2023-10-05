package ru.otus.spring.springbootquiz.service;

import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.domain.Result;

import java.util.List;

public interface QuizIOService {

    void printWelcomeMessage();

    void printQuestion(String question);

    void printAllQuestionsWithAnswers(List<Question> questions);

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
