package ru.otus.spring.ajaxlibrary.service;


import ru.otus.spring.ajaxlibrary.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long bookId);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    void deleteBookById(Long bookId);
}
