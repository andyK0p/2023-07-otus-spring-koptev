package ru.otus.spring.datalibrary.mappers;

import ru.otus.spring.datalibrary.data.entity.Author;
import ru.otus.spring.datalibrary.dto.AuthorDto;

public class AuthorMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }
}
