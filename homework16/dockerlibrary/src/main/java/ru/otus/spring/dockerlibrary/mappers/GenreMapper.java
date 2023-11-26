package ru.otus.spring.dockerlibrary.mappers;

import ru.otus.spring.dockerlibrary.data.entity.Genre;
import ru.otus.spring.dockerlibrary.dto.GenreDto;


public class GenreMapper {

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toDomainObject(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
