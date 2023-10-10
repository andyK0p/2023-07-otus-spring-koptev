package ru.otus.spring.jdbclibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbclibrary.dao.BookDao;
import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.model.BookDto;
import ru.otus.spring.jdbclibrary.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return Optional.ofNullable(bookDao.getById(bookId));
    }

    @Override
    public void createBook(BookDto book) {
        bookDao.create(book);
    }

    @Override
    public void updateBook(BookDto book) {
        bookDao.update(book);
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }
}
