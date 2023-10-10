package ru.otus.spring.jdbclibrary.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbclibrary.domain.Author;
import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.domain.Genre;
import ru.otus.spring.jdbclibrary.model.BookDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ДАО книги")
@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {

    private static final Long EXISTING_BOOK_ID = 3L;

    private static final String EXISTING_BOOK_TITLE = "Moby Dick";

    private static final Integer EXISTING_BOOK_PAGE_COUNT = 378;

    private static final Long EXISTING_AUTHOR_ID = 3L;

    private static final String EXISTING_AUTHOR_NAME = "Herman Melville";

    private static final Long EXISTING_GENRE_ID = 3L;

    private static final String EXISTING_GENRE_NAME = "Adventures";

    @Autowired
    private BookDaoImpl bookDao;

    private Book expectedBook;

    @BeforeEach
    void setUp() {
        Author author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Genre genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        this.expectedBook = new Book(
                EXISTING_BOOK_ID,
                EXISTING_BOOK_TITLE,
                author,
                EXISTING_BOOK_PAGE_COUNT,
                genre);
    }

    @Test
    @DisplayName("получает книгу по id")
    void test_getById() {
        Book actual = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(this.expectedBook);
    }

    @Test
    @DisplayName("создает книгу")
    void test_create() {
        BookDto bookDto = new BookDto(4L, "Benito Cereno", EXISTING_AUTHOR_ID,
                86, EXISTING_GENRE_ID);
        this.expectedBook.setId(bookDto.getBookId());
        this.expectedBook.setTitle(bookDto.getTitle());
        this.expectedBook.setPageCount(bookDto.getPageCount());

        bookDao.create(bookDto);
        Book actual = bookDao.getById(4L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(this.expectedBook);
    }

    @Test
    @DisplayName("обновляет книгу")
    void test_update() {
        BookDto bookDto = new BookDto(EXISTING_BOOK_ID, "Benito Cereno", EXISTING_AUTHOR_ID,
                86, EXISTING_GENRE_ID);
        this.expectedBook.setId(bookDto.getBookId());
        this.expectedBook.setTitle(bookDto.getTitle());
        this.expectedBook.setPageCount(bookDto.getPageCount());

        bookDao.update(bookDto);
        Book actual = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(this.expectedBook);
    }

    @Test
    @DisplayName("удаляет книгу по id")
    void test_deleteById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID)).doesNotThrowAnyException();
        bookDao.deleteById(EXISTING_BOOK_ID);
        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("получает список всех книг")
    void test_getAll() {
        List<Book> bookList = bookDao.getAll();
        assertThat(bookList).contains(this.expectedBook);
    }
}