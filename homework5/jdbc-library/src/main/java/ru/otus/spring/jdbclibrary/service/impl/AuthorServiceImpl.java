package ru.otus.spring.jdbclibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbclibrary.dao.AuthorDao;
import ru.otus.spring.jdbclibrary.domain.Author;
import ru.otus.spring.jdbclibrary.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorById(Long authorId) {
        return authorDao.getById(authorId);
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.create(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteAuthorById(Long authorId) {
        authorDao.deleteById(authorId);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }
}
