package ru.otus.spring.datalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.datalibrary.data.entity.Book;
import ru.otus.spring.datalibrary.data.entity.Comment;
import ru.otus.spring.datalibrary.data.repository.BookRepository;
import ru.otus.spring.datalibrary.dto.BookOutputDto;
import ru.otus.spring.datalibrary.dto.CommentOutputDto;
import ru.otus.spring.datalibrary.exception.NotFoundException;
import ru.otus.spring.datalibrary.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    @Transactional(readOnly = true)
    @Override
    public List<BookOutputDto> getAllBooks() {
        return repo.findAll().stream().map(BookOutputDto::fromDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookOutputDto getBookById(Long bookId) {
        return repo.findById(bookId).map(BookOutputDto::fromDomain).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void createBook(Book book) {
        repo.save(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        repo.save(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long bookId) {
        repo.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllCommentsByBook(Long bookId) {
        Book book = repo.findById(bookId).orElseThrow(NotFoundException::new);
        return book.getComments().stream().map(CommentOutputDto::fromDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addComment(Long bookId, Comment comment) {
        Book book = repo.findById(bookId).orElseThrow(NotFoundException::new);
        book.addComment(comment);
        repo.save(book);
    }

    @Override
    @Transactional
    public void removeComment(Long bookId, Comment comment) {
        Book book = repo.findById(bookId).orElseThrow(NotFoundException::new);
        book.removeComment(comment);
        repo.save(book);
    }

    @Override
    @Transactional
    public void updateComment(Long bookId, Comment oldComment, Comment newComment) {
        Book book = repo.findById(bookId).orElseThrow(NotFoundException::new);
        book.removeComment(oldComment);
        book.addComment(newComment);
        repo.save(book);
    }
}
