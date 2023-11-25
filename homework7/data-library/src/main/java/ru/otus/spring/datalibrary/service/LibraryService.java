package ru.otus.spring.datalibrary.service;


import ru.otus.spring.datalibrary.dto.AuthorDto;
import ru.otus.spring.datalibrary.dto.BookDto;
import ru.otus.spring.datalibrary.dto.output.CommentOutputDto;
import ru.otus.spring.datalibrary.dto.GenreDto;
import ru.otus.spring.datalibrary.dto.input.BookInputDto;
import ru.otus.spring.datalibrary.dto.input.CommentInputDto;

import java.util.List;

public interface LibraryService {

    List<AuthorDto> listAllAuthors();

    List<GenreDto> listAllGenres();

    List<BookDto> listAllBooks();

    BookDto printSingleBookById(Long bookId);

    void addBookToLibrary(BookInputDto bookDto);

    void updateBook(BookInputDto bookDto);

    void deleteBook(Long bookId);

    List<CommentOutputDto> listAllCommentsForBook(Long bookId);

    CommentOutputDto printCommentById(Long commentId);

    void addCommentToBook(CommentInputDto commentDto);

    void updateComment(CommentInputDto commentDto);

    void deleteComment(Long commentId);
}
