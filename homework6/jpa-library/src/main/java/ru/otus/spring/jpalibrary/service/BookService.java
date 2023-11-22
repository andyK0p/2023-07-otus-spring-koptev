package ru.otus.spring.jpalibrary.service;


import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dto.BookOutputDto;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;

public interface BookService {

    List<BookOutputDto> getAllBooks();

    BookOutputDto getBookById(Long bookId);

    void createBook(Book book);

    void updateBook(Book book);

    void deleteBookById(Long bookId);

    List<CommentOutputDto> getAllCommentsByBook(Long bookId);

    void addComment(Long bookId, Comment comment);

    void removeComment(Long bookId, Comment comment);

    void updateComment(Long bookId, Comment oldComment, Comment newComment);
}
