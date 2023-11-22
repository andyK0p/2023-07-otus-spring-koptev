package ru.otus.spring.jpalibrary.dao.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jpalibrary.dao.entity.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Жанра должен")
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {

    private static final int GENRES_COUNT = 3;

    private static final long FIRST_GENRE_ID = 1L;

    private static final String UPDATED_GENRE_NAME = "Fantasy";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepositoryImpl repo;

    @Test
    @DisplayName("загружать все жанры")
    void test_findAll() {
        List<Genre> genres = repo.findAll();
        assertThat(genres).isNotNull().hasSize(GENRES_COUNT);
    }

    @Test
    @DisplayName("загружать один жанр по id")
    void test_findById() {
        Optional<Genre> actualOpt = repo.findById(FIRST_GENRE_ID);
        Genre expected = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(actualOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("сохранять жанр")
    void test_save() {
        Genre genre = new Genre(null, UPDATED_GENRE_NAME);
        repo.save(genre);
        assertThat(genre.getId()).isNotNull();

        Genre actual = em.find(Genre.class, genre.getId());
        assertThat(actual).isNotNull().matches(a -> a.getName().equals(UPDATED_GENRE_NAME));
    }

    @Test
    @DisplayName("обновлять наименование жанра")
    void test_updateGenreName() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        Genre copy = new Genre(genre.getId(), genre.getName());
        String oldName = copy.getName();
        em.detach(genre);

        copy.setName(UPDATED_GENRE_NAME);
        repo.save(copy);

        Genre updated = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(updated.getName()).isNotEqualTo(oldName);
    }

    @Test
    @DisplayName("удалять жанр по id")
    void test_deleteById() {
        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(genre).isNotNull();
        em.detach(genre);

        repo.deleteById(FIRST_GENRE_ID);
        Genre deleted = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(deleted).isNull();
    }
}