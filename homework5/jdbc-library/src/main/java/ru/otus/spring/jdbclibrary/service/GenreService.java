package ru.otus.spring.jdbclibrary.service;

import ru.otus.spring.jdbclibrary.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre getGenreById(Long genreId);

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenreById(Long genreId);

    List<Genre> getAllGenres();
}
