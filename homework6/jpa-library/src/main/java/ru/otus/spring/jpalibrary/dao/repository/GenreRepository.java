package ru.otus.spring.jpalibrary.dao.repository;

import ru.otus.spring.jpalibrary.dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Genre save(Genre genre);

    void deleteById(Long id);
}
