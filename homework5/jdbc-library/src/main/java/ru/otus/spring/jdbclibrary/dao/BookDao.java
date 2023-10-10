package ru.otus.spring.jdbclibrary.dao;

import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.model.BookDto;

import java.util.List;

public interface BookDao {

    Book getById(Long id);

    void create(BookDto book);

    void update(BookDto book);

    void deleteById(Long id);

    List<Book> getAll();
}
