package ru.otus.spring.actuatorlibrary.service;


import ru.otus.spring.actuatorlibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long authorId);

    AuthorDto addAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(AuthorDto authorDto);

    void deleteAuthorById(Long authorId);
}
