package ru.otus.spring.jdbclibrary.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbclibrary.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@DisplayName("ДАО Автора")
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final Long EXISTING_AUTHOR_ID = 1L;

    private static final String EXISTING_AUTHOR_NAME = "Frank Herbert";

    @Autowired
    private AuthorDaoImpl dao;

    @Test
    @DisplayName("получает автора по id")
    void test_getById() {
        Author expected = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actual = dao.getById(EXISTING_AUTHOR_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("создает нового автора")
    void test_create() {
        Author expected = new Author(4L, "Jack London");
        dao.create(expected);
        Author actual = dao.getById(4L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("обновляет автора")
    void test_update() {
        Author expected = new Author(EXISTING_AUTHOR_ID, "Jack London");
        dao.update(expected);
        Author actual = dao.getById(EXISTING_AUTHOR_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("удаляет автора по id")
    void test_deleteById() {
        assertThatCode(() -> dao.getById(EXISTING_AUTHOR_ID)).doesNotThrowAnyException();
        dao.deleteById(EXISTING_AUTHOR_ID);
        assertThatThrownBy(() -> dao.getById(EXISTING_AUTHOR_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("получает список всех авторов")
    void test_getAll() {
        Author expected = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> authors = dao.getAll();
        assertThat(authors).contains(expected);
    }
}