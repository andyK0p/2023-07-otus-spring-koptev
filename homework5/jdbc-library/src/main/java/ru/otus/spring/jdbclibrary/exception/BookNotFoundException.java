package ru.otus.spring.jdbclibrary.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book is not present in database");
    }
}
