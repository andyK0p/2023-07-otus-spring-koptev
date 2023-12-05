package ru.otus.spring.actuatorlibrary.mappers;

import ru.otus.spring.actuatorlibrary.data.entity.Genre;
import ru.otus.spring.actuatorlibrary.dto.GenreDto;


public class GenreMapper {

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toDomainObject(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
