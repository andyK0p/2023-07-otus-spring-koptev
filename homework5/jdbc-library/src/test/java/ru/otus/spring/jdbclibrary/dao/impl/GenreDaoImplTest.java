package ru.otus.spring.jdbclibrary.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbclibrary.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@DisplayName("ДАО жанров")
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

    private static final Long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "Sci-Fi";

    @Autowired
    private GenreDaoImpl dao;

    @Test
    @DisplayName("получает жанр по id")
    void test_getById() {
        Genre expected = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actual = dao.getById(EXISTING_GENRE_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("создает новый жанр")
    void test_create() {
        Genre expected = new Genre(4L, "Drama");
        dao.create(expected);
        Genre actual = dao.getById(4L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("обновляет жанр")
    void test_update() {
        Genre expected = new Genre(EXISTING_GENRE_ID, "Comedy");
        dao.update(expected);
        Genre actual = dao.getById(EXISTING_GENRE_ID);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("удаляет жанр по id")
    void test_deleteById() {
        assertThatCode(() -> dao.getById(EXISTING_GENRE_ID)).doesNotThrowAnyException();
        dao.deleteById(EXISTING_GENRE_ID);
        assertThatThrownBy(() -> dao.getById(EXISTING_GENRE_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("получает список всех жанров")
    void test_getAll() {
        Genre expected = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> genres = dao.getAll();
        assertThat(genres).contains(expected);
    }
}