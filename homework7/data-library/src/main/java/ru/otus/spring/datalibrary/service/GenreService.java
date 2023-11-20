package ru.otus.spring.datalibrary.service;


import ru.otus.spring.datalibrary.data.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();

    Genre getGenreById(Long genreId);

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenreById(Long genreId);
}
