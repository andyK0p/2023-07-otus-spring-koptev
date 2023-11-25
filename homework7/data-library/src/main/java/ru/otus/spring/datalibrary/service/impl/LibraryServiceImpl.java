package ru.otus.spring.datalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.datalibrary.dto.AuthorDto;
import ru.otus.spring.datalibrary.dto.BookDto;
import ru.otus.spring.datalibrary.dto.CommentDto;
import ru.otus.spring.datalibrary.dto.output.CommentOutputDto;
import ru.otus.spring.datalibrary.dto.GenreDto;
import ru.otus.spring.datalibrary.dto.input.BookInputDto;
import ru.otus.spring.datalibrary.dto.input.CommentInputDto;
import ru.otus.spring.datalibrary.mappers.CommentMapper;
import ru.otus.spring.datalibrary.service.AuthorService;
import ru.otus.spring.datalibrary.service.BookService;
import ru.otus.spring.datalibrary.service.GenreService;
import ru.otus.spring.datalibrary.service.LibraryService;
import ru.otus.spring.datalibrary.service.CommentService;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    private final CommentService commentService;


    @Override
    public List<AuthorDto> listAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Override
    public List<GenreDto> listAllGenres() {
        return genreService.getAllGenres();
    }

    @Override
    public List<BookDto> listAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public BookDto printSingleBookById(Long bookId) {
        return bookService.getBookById(bookId);
    }

    @Override
    public void addBookToLibrary(BookInputDto bookInput) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(bookInput.getTitle());
        bookDto.setPageCount(bookInput.getPageCount());
        bookDto.setAuthor(authorService.getAuthorById(bookInput.getAuthorId()));
        bookDto.setGenre(genreService.getGenreById(bookInput.getGenreId()));
        bookService.createBook(bookDto);
    }

    @Override
    public void updateBook(BookInputDto bookInput) {
        BookDto bookDto = bookService.getBookById(bookInput.getId());
        if (!bookInput.getTitle().isEmpty()) {
            bookDto.setTitle(bookInput.getTitle());
        }
        if (Objects.nonNull(bookInput.getPageCount()) && bookInput.getPageCount() != 0) {
            bookDto.setPageCount(bookInput.getPageCount());
        }
        bookDto.setAuthor(authorService.getAuthorById(bookInput.getAuthorId()));
        bookDto.setGenre(genreService.getGenreById(bookInput.getGenreId()));
        bookService.updateBook(bookDto);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @Override
    public List<CommentOutputDto> listAllCommentsForBook(Long bookId) {
        return commentService.getCommentsByBookId(bookId).stream()
                .map(CommentMapper::toOutputDto).collect(Collectors.toList());
    }

    @Override
    public CommentOutputDto printCommentById(Long commentId) {
        return CommentMapper.toOutputDto(commentService.getCommentById(commentId));
    }

    @Override
    public void addCommentToBook(CommentInputDto commentInput) {
        CommentDto dto = new CommentDto();
        dto.setName(commentInput.getName());
        dto.setText(commentInput.getText());
        dto.setBook(bookService.getBookById(commentInput.getBookId()));
        commentService.addComment(dto);
    }

    @Override
    public void updateComment(CommentInputDto commentInput) {
        CommentDto dto = new CommentDto();
        dto.setId(commentInput.getId());
        dto.setName(commentInput.getName());
        dto.setText(commentInput.getText());
        dto.setBook(bookService.getBookById(commentInput.getBookId()));
        commentService.updateComment(dto);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}
