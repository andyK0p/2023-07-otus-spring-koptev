package ru.otus.spring.mvclibrary.service;


import ru.otus.spring.mvclibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long authorId);

    AuthorDto addAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(AuthorDto authorDto);

    void deleteAuthorById(Long authorId);
}
