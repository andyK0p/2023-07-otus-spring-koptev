package ru.otus.spring.ajaxlibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.ajaxlibrary.data.entity.Author;
import ru.otus.spring.ajaxlibrary.data.repository.AuthorRepository;
import ru.otus.spring.ajaxlibrary.dto.AuthorDto;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;
import ru.otus.spring.ajaxlibrary.mappers.AuthorMapper;
import ru.otus.spring.ajaxlibrary.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return repo.findAll().stream().map(AuthorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(Long authorId) {
        return repo.findById(authorId).map(AuthorMapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = AuthorMapper.toDomainObject(authorDto);
        Author added = repo.save(author);
        return AuthorMapper.toDto(added);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        Author author = AuthorMapper.toDomainObject(authorDto);
        Author updated = repo.save(author);
        return AuthorMapper.toDto(updated);
    }

    @Override
    public void deleteAuthorById(Long authorId) {
        repo.deleteById(authorId);
    }
}
