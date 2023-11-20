package ru.otus.spring.datalibrary.service;


import ru.otus.spring.datalibrary.data.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(Long authorId);

    void addAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthorById(Long authorId);
}
