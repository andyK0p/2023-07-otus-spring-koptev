package ru.otus.spring.ajaxlibrary.exception;

public class NonNullException extends RuntimeException {
    public NonNullException(String message) {
        super(message + " must be null for CREATE!");
    }
}
