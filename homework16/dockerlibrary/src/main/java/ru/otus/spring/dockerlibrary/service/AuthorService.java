package ru.otus.spring.dockerlibrary.service;


import ru.otus.spring.dockerlibrary.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long authorId);

    AuthorDto addAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(AuthorDto authorDto);

    void deleteAuthorById(Long authorId);
}
