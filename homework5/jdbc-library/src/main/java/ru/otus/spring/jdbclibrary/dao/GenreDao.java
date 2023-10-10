package ru.otus.spring.jdbclibrary.dao;

import ru.otus.spring.jdbclibrary.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre getById(Long id);

    void create(Genre genre);

    void update(Genre genre);

    void deleteById(Long id);

    List<Genre> getAll();
}
