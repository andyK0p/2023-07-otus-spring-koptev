package ru.otus.spring.springshellquiz.service;

import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.domain.Result;

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

    void printUnderageCheckNotice();

    String getAfterAgeCheckMessage();

    String getAgeCheckFailed();

    int readAnswer();

    String readInput();
}
