package ru.otus.spring.datalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.datalibrary.data.entity.Author;
import ru.otus.spring.datalibrary.data.repository.AuthorRepository;
import ru.otus.spring.datalibrary.exception.NotFoundException;
import ru.otus.spring.datalibrary.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    @Override
    public List<Author> getAllAuthors() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(Long authorId) {
        return repo.findById(authorId).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void addAuthor(Author author) {
        repo.save(author);
    }

    @Override
    @Transactional
    public void updateAuthor(Author author) {
        repo.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(Long authorId) {
        repo.deleteById(authorId);
    }
}
