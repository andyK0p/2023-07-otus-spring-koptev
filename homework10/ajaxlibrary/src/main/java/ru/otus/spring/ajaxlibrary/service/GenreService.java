package ru.otus.spring.ajaxlibrary.service;


import ru.otus.spring.ajaxlibrary.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAllGenres();

    GenreDto getGenreById(Long genreId);

    GenreDto addGenre(GenreDto genreDto);

    GenreDto updateGenre(GenreDto genreDto);

    void deleteGenreById(Long genreId);
}
