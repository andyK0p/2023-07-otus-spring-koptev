package ru.otus.spring.datalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.datalibrary.data.entity.Author;
import ru.otus.spring.datalibrary.data.entity.Book;
import ru.otus.spring.datalibrary.data.entity.Comment;
import ru.otus.spring.datalibrary.data.entity.Genre;
import ru.otus.spring.datalibrary.data.repository.BookRepository;
import ru.otus.spring.datalibrary.dto.BookDto;
import ru.otus.spring.datalibrary.dto.BookOutputDto;
import ru.otus.spring.datalibrary.dto.CommentDto;
import ru.otus.spring.datalibrary.dto.CommentOutputDto;
import ru.otus.spring.datalibrary.exception.NotFoundException;
import ru.otus.spring.datalibrary.service.AuthorService;
import ru.otus.spring.datalibrary.service.BookService;
import ru.otus.spring.datalibrary.service.GenreService;
import ru.otus.spring.datalibrary.service.LibraryService;
import ru.otus.spring.datalibrary.service.CommentService;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    private final CommentService commentService;

    private final BookRepository bookRepository;

    @Override
    public List<Author> listAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Override
    public List<Genre> listAllGenres() {
        return genreService.getAllGenres();
    }

    @Override
    public List<BookOutputDto> listAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public BookOutputDto getBookById(Long bookId) {
        return bookService.getBookById(bookId);
    }

    @Override
    public void addBookToLibrary(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setPageCount(bookDto.getPageCount());
        book.setAuthor(authorService.getAuthorById(bookDto.getAuthorId()));
        book.setGenre(genreService.getGenreById(bookDto.getGenreId()));
        bookService.createBook(book);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(NotFoundException::new);
        if (!bookDto.getTitle().isEmpty()) {
            book.setTitle(bookDto.getTitle());
        }
        if (Objects.nonNull(bookDto.getPageCount()) && bookDto.getPageCount() != 0) {
            book.setPageCount(bookDto.getPageCount());
        }
        book.setAuthor(authorService.getAuthorById(bookDto.getAuthorId()));
        book.setGenre(genreService.getGenreById(bookDto.getGenreId()));
        bookService.updateBook(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @Override
    public List<CommentOutputDto> listAllCommentsForBook(Long bookId) {
        return bookService.getAllCommentsByBook(bookId);
    }

    @Override
    public CommentOutputDto getCommentById(Long commentId) {
        return CommentOutputDto.fromDomain(commentService.getById(commentId));
    }

    @Override
    public void addCommentToBook(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setText(commentDto.getText());
        bookService.addComment(commentDto.getBookId(), comment);
    }

    @Override
    @Transactional
    public void updateComment(CommentDto commentDto) {
        Comment oldComment = commentService.getById(commentDto.getId());
        Comment newComment = new Comment(oldComment.getId(), commentDto.getName(), commentDto.getText(), null);
        bookService.updateComment(commentDto.getBookId(), oldComment, newComment);
    }

    @Override
    @Transactional
    public void deleteComment(CommentDto dto) {
        Comment comment = commentService.getById(dto.getId());
        bookService.removeComment(dto.getBookId(), comment);
    }
}
