package ru.otus.spring.actuatorlibrary.mappers;

import ru.otus.spring.actuatorlibrary.data.entity.Author;
import ru.otus.spring.actuatorlibrary.dto.AuthorDto;

public class AuthorMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }
}
