package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.model.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(Long bookId);

    void createBook(BookDto book);

    void updateBook(BookDto book);

    void deleteBookById(Long bookId);

    List<Book> getAllBooks();
}
