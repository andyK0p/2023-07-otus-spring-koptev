package ru.otus.spring.actuatorlibrary.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Requested entity is not present in database");
    }
}
