package ru.otus.spring.jpalibrary.dao.repository;

import ru.otus.spring.jpalibrary.dao.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);
}
