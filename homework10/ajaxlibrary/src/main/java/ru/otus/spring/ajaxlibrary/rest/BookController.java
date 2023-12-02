package ru.otus.spring.ajaxlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.ajaxlibrary.dto.BookDto;
import ru.otus.spring.ajaxlibrary.dto.input.BookInputDto;
import ru.otus.spring.ajaxlibrary.exception.MustNotBeNullException;
import ru.otus.spring.ajaxlibrary.exception.NonNullException;
import ru.otus.spring.ajaxlibrary.service.AuthorService;
import ru.otus.spring.ajaxlibrary.service.BookService;
import ru.otus.spring.ajaxlibrary.service.GenreService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    //http://localhost/api/books
    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    //http://localhost/api/books/1
    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    //http://localhost/api/books
    @PostMapping("/api/books")
    public BookDto addNewBook(@RequestBody BookInputDto inputDto) {
        if (Objects.nonNull(inputDto.getId())) {
            throw new NonNullException("Book Id");
        }
        BookDto dto = new BookDto();
        dto.setTitle(inputDto.getTitle());
        dto.setPageCount(inputDto.getPageCount());
        dto.setAuthor(authorService.getAuthorById(inputDto.getAuthorId()));
        dto.setGenre(genreService.getGenreById(inputDto.getGenreId()));
        return bookService.createBook(dto);
    }

    //http://localhost/api/books
    @PutMapping("/api/books")
    public BookDto updateBook(@RequestBody BookInputDto inputDto) {
        if (Objects.isNull(inputDto.getId())) {
            throw new MustNotBeNullException("Book Id");
        }
        BookDto dto = new BookDto();
        dto.setId(inputDto.getId());
        dto.setTitle(inputDto.getTitle());
        dto.setPageCount(inputDto.getPageCount());
        dto.setAuthor(authorService.getAuthorById(inputDto.getAuthorId()));
        dto.setGenre(genreService.getGenreById(inputDto.getGenreId()));
        return bookService.updateBook(dto);
    }

    //http://localhost/api/books/1
    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);
    }
}
