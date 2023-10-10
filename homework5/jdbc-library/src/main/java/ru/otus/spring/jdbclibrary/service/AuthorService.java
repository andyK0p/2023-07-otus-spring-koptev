package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getAuthorById(Long authorId);

    void addAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthorById(Long authorId);

    List<Author> getAllAuthors();
}
