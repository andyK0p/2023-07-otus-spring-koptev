package ru.otus.spring.jdbclibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbclibrary.domain.Author;
import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.domain.Genre;
import ru.otus.spring.jdbclibrary.exception.BookNotFoundException;
import ru.otus.spring.jdbclibrary.model.BookDto;
import ru.otus.spring.jdbclibrary.service.AuthorService;
import ru.otus.spring.jdbclibrary.service.BookService;
import ru.otus.spring.jdbclibrary.service.GenreService;
import ru.otus.spring.jdbclibrary.service.LibraryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Override
    public void addBookToLibrary(BookDto bookDto) {
        bookService.createBook(bookDto);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        bookService.getBookById(bookDto.getBookId())
                .ifPresentOrElse(value -> bookService.updateBook(bookDto), BookNotFoundException::new);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookService.getBookById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> listAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public List<Author> listAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Override
    public List<Genre> listAllGenres() {
        return genreService.getAllGenres();
    }
}
