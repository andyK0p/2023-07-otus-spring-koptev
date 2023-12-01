package ru.otus.spring.ajaxlibrary.exception;

public class MustNotBeNullException extends RuntimeException {
    public MustNotBeNullException(String message) {
        super(message + " must be specified for UPDATE!");
    }
}
