package ru.otus.spring.jpalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.jpalibrary.dao.entity.Author;
import ru.otus.spring.jpalibrary.dao.repository.AuthorRepository;
import ru.otus.spring.jpalibrary.exception.NotFoundException;
import ru.otus.spring.jpalibrary.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repo;

    @Override
    @Transactional(readOnly = true)
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
