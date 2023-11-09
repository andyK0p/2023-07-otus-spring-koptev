package ru.otus.spring.jpalibrary.service;


import ru.otus.spring.jpalibrary.dao.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(Long authorId);

    void addAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthorById(Long authorId);
}
