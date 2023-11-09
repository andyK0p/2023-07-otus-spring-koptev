package ru.otus.spring.jpalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.repository.BookRepository;
import ru.otus.spring.jpalibrary.exception.NotFoundException;
import ru.otus.spring.jpalibrary.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long bookId) {
        return repo.findById(bookId).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void createBook(Book book) {
        repo.save(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        repo.save(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long bookId) {
        repo.deleteById(bookId);
    }
}
