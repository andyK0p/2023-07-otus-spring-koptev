package ru.otus.spring.jpalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jpalibrary.dao.entity.Author;
import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dao.entity.Genre;
import ru.otus.spring.jpalibrary.dto.BookDto;
import ru.otus.spring.jpalibrary.dto.CommentDto;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;
import ru.otus.spring.jpalibrary.service.AuthorService;
import ru.otus.spring.jpalibrary.service.BookService;
import ru.otus.spring.jpalibrary.service.GenreService;
import ru.otus.spring.jpalibrary.service.LibraryService;
import ru.otus.spring.jpalibrary.service.CommentService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    private final CommentService commentService;

    @Override
    public List<Author> listAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Override
    public List<Genre> listAllGenres() {
        return genreService.getAllGenres();
    }

    @Override
    public List<Book> listAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    public Book getBookById(Long bookId) {
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
        Author author = authorService.getAuthorById(bookDto.getAuthorId());
        Genre genre = genreService.getGenreById(bookDto.getGenreId());
        Book book = new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getPageCount(), author, genre);
        bookService.updateBook(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @Override
    public List<CommentOutputDto> listAllCommentsForBook(Long bookId) {
        return commentService.getAllCommentsByBookId(bookId);
    }

    @Override
    public CommentOutputDto getCommentById(Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @Override
    public void addCommentToBook(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setText(commentDto.getText());
        Book book = bookService.getBookById(commentDto.getBookId());
        comment.setBook(book);
        commentService.addComment(comment);
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        Comment comment = commentService.getById(commentDto.getId());
        if (commentDto.getName() != null) {
            comment.setName(commentDto.getName());
        }
        if (commentDto.getText() != null) {
            comment.setText(commentDto.getText());
        }
        if (commentDto.getBookId() != null) {
            Book book = bookService.getBookById(commentDto.getBookId());
            if (book != null) {
                comment.setBook(book);
            }
        }
        commentService.updateComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}
