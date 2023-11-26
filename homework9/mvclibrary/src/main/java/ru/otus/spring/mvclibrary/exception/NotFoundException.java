package ru.otus.spring.mvclibrary.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Requested entity is not present in database");
    }
}
