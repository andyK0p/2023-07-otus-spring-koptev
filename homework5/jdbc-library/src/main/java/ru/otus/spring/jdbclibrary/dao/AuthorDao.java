package ru.otus.spring.jdbclibrary.dao;

import ru.otus.spring.jdbclibrary.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(Long id);

    void create(Author author);

    void update(Author author);

    void deleteById(Long id);

    List<Author> getAll();
}
