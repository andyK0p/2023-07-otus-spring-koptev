package ru.otus.spring.actuatorlibrary.service;


import ru.otus.spring.actuatorlibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();

    GenreDto getGenreById(Long genreId);

    GenreDto addGenre(GenreDto genreDto);

    GenreDto updateGenre(GenreDto genreDto);

    void deleteGenreById(Long genreId);
}
