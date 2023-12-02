package ru.otus.spring.ajaxlibrary.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.ajaxlibrary.data.entity.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Комментариев должен")
class CommentRepositoryTest {

    private static final long BOOK_ID = 3L;

    private static final int COMMENTS_SIZE = 2;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository repo;

    @Test
    @DisplayName("загружать комментарии по id книги")
    void test_findByBookId() {
        List<Comment> comments = repo.findByBookId(BOOK_ID);
        assertThat(comments).isNotNull().hasSize(COMMENTS_SIZE)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> !b.getText().equals(""))
                .allMatch(b -> b.getBook().getId().equals(BOOK_ID));
    }
}