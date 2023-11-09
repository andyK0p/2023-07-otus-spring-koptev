package ru.otus.spring.jpalibrary.service;


import ru.otus.spring.jpalibrary.dao.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long bookId);

    void createBook(Book book);

    void updateBook(Book book);

    void deleteBookById(Long bookId);
}
