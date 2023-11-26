package ru.otus.spring.dockerlibrary.mappers;

import ru.otus.spring.dockerlibrary.data.entity.Author;
import ru.otus.spring.dockerlibrary.dto.AuthorDto;

public class AuthorMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }
}
