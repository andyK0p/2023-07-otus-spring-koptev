package ru.otus.spring.jpalibrary.dao.repository;

import ru.otus.spring.jpalibrary.dao.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author save(Author author);

    void deleteById(Long id);
}
