package ru.otus.spring.dockerlibrary.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.dockerlibrary.data.entity.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Книг должен")
class BookRepositoryTest {

    private static final int BOOKS_COUNT = 3;

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository repo;

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
}