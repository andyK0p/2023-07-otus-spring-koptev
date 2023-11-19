package ru.otus.spring.jpalibrary.service;


import ru.otus.spring.jpalibrary.dao.entity.Author;
import ru.otus.spring.jpalibrary.dao.entity.Genre;
import ru.otus.spring.jpalibrary.dto.BookDto;
import ru.otus.spring.jpalibrary.dto.BookOutputDto;
import ru.otus.spring.jpalibrary.dto.CommentDto;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;

public interface LibraryService {

    List<Author> listAllAuthors();

    List<Genre> listAllGenres();

    List<BookOutputDto> listAllBooks();

    BookOutputDto getBookById(Long bookId);

    void addBookToLibrary(BookDto bookDto);

    void updateBook(BookDto bookDto);

    void deleteBook(Long bookId);

    List<CommentOutputDto> listAllCommentsForBook(Long bookId);

    CommentOutputDto getCommentById(Long commentId);

    void addCommentToBook(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteComment(CommentDto commentDto);
}
