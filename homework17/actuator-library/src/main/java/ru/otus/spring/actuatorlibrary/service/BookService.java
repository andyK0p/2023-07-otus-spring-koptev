package ru.otus.spring.actuatorlibrary.service;


import ru.otus.spring.actuatorlibrary.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long bookId);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    void deleteBookById(Long bookId);

    long countBooks();
}
