package ru.otus.spring.dockerlibrary.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Requested entity is not present in database");
    }
}
