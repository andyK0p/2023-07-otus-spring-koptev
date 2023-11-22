package ru.otus.spring.jpalibrary.service;


import ru.otus.spring.jpalibrary.dao.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();

    Genre getGenreById(Long genreId);

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenreById(Long genreId);
}
