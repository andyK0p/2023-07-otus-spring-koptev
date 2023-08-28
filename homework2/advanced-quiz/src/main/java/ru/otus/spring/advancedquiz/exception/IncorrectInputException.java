package ru.otus.spring.advancedquiz.exception;

public class IncorrectInputException extends QuizException {

    public IncorrectInputException() {
        super("Incorrect input! Must be 'y' or 'n'.");
    }
}
