package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.domain.Author;
import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.domain.Genre;
import ru.otus.spring.jdbclibrary.model.BookDto;

import java.util.List;

public interface LibraryService {

    void addBookToLibrary(BookDto bookDto);

    void updateBook(BookDto bookDto);

    void deleteBook(Long bookId);

    Book getBookById(Long bookId);

    List<Book> listAllBooks();

    List<Author> listAllAuthors();

    List<Genre> listAllGenres();
}
