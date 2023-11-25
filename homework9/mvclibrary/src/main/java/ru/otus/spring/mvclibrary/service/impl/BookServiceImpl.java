package ru.otus.spring.mvclibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.mvclibrary.data.entity.Book;
import ru.otus.spring.mvclibrary.data.repository.BookRepository;
import ru.otus.spring.mvclibrary.dto.BookDto;
import ru.otus.spring.mvclibrary.exception.NotFoundException;
import ru.otus.spring.mvclibrary.mappers.BookMapper;
import ru.otus.spring.mvclibrary.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    @Override
    public List<BookDto> getAllBooks() {
        return repo.findAll().stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long bookId) {
        return repo.findById(bookId).map(BookMapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.toDomainObject(bookDto);
        Book added = repo.save(book);
        return BookMapper.toDto(added);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        Book book = BookMapper.toDomainObject(bookDto);
        Book updated = repo.save(book);
        return BookMapper.toDto(updated);
    }

    @Override
    public void deleteBookById(Long bookId) {
        repo.deleteById(bookId);
    }
}
