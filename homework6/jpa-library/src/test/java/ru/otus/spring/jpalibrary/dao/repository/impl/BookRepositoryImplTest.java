package ru.otus.spring.jpalibrary.dao.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jpalibrary.dao.entity.Author;
import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.entity.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Книг должен")
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {

    private static final int BOOKS_COUNT = 3;

    private static final long FIRST_BOOK_ID = 1L;

    private static final String AUTHOR_NAME = "Pamela L. Travers";

    private static final String GENRE_NAME = "Kids fairytale";

    private static final int PAGE_COUNT = 385;

    private static final String UPDATED_BOOK_NAME = "Mary Poppins";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepositoryImpl repo;

    @Test
    @DisplayName("загружать все книги")
    void test_findAll() {
        List<Book> books = repo.findAll();
        assertThat(books).isNotNull().hasSize(BOOKS_COUNT)
                .allMatch(b -> !b.getTitle().equals(""))
                .allMatch(b -> b.getPageCount() > 0)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getComments().size() > 0);
    }

    @Test
    @DisplayName("загружать одну книгу по id")
    void test_findById() {
        Optional<Book> actualOpt = repo.findById(FIRST_BOOK_ID);
        Book expected = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("сохранять книгу")
    void test_save() {
        Author author = new Author(null, AUTHOR_NAME);
        Genre genre = new Genre(null, GENRE_NAME);
        Book book = new Book(null, UPDATED_BOOK_NAME, PAGE_COUNT, author, genre);
        repo.save(book);
        assertThat(book.getId()).isNotNull();

        Book actual = em.find(Book.class, book.getId());
        assertThat(actual).isNotNull()
                .matches(b -> b.getTitle().equals(UPDATED_BOOK_NAME))
                .matches(b -> b.getPageCount().equals(PAGE_COUNT))
                .matches(b -> b.getAuthor() != null && b.getAuthor().getFullName().equals(AUTHOR_NAME))
                .matches(b -> b.getGenre() != null && b.getGenre().getName().equals(GENRE_NAME));
    }

    @Test
    @DisplayName("обновлять наименование книги")
    void test_updateBookTitle() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Book copy = new Book(book.getId(), book.getTitle(), book.getPageCount(), book.getAuthor(), book.getGenre());
        String oldTitle = copy.getTitle();
        em.detach(book);

        copy.setTitle(UPDATED_BOOK_NAME);
        repo.save(copy);

        Book updated = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(updated.getTitle()).isNotEqualTo(oldTitle);
    }

    @Test
    @DisplayName("удалять книгу по id")
    void test_deleteById() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        em.detach(book);

        repo.deleteById(FIRST_BOOK_ID);
        Book deleted = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deleted).isNull();
    }
}