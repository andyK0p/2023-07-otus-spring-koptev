package ru.otus.spring.jpalibrary.dao.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jpalibrary.dao.entity.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Автора должен")
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {

    private static final int AUTHORS_COUNT = 3;

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final String UPDATED_AUTHOR_NAME = "Arthur Clarke";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepositoryImpl repo;

    @Test
    @DisplayName("загружать всех авторов")
    void test_findAll() {
        List<Author> authors = repo.findAll();
        assertThat(authors).isNotNull().hasSize(AUTHORS_COUNT);
    }

    @Test
    @DisplayName("загружать одного автора по id")
    void test_findById() {
        Optional<Author> actualOpt = repo.findById(FIRST_AUTHOR_ID);
        Author expected = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("сохранять автора")
    void test_save() {
        Author author = new Author(null, UPDATED_AUTHOR_NAME);
        repo.save(author);
        assertThat(author.getId()).isNotNull();

        Author actual = em.find(Author.class, author.getId());
        assertThat(actual).isNotNull().matches(a -> a.getFullName().equals(UPDATED_AUTHOR_NAME));
    }

    @Test
    @DisplayName("обновлять имя автора")
    void test_updateAuthorName() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        Author copy = new Author(author.getId(), author.getFullName());
        String oldName = copy.getFullName();
        em.detach(author);

        copy.setFullName(UPDATED_AUTHOR_NAME);
        repo.save(copy);

        Author updated = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(updated.getFullName()).isNotEqualTo(oldName);
    }

    @Test
    @DisplayName("удалять автора по id")
    void test_deleteById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(author).isNotNull();
        em.detach(author);

        repo.deleteById(FIRST_AUTHOR_ID);
        Author deleted = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(deleted).isNull();
    }
}