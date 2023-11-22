package ru.otus.spring.jpalibrary.dao.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Комментариев должен")
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    private static final long BOOK_ID = 3L;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final String UPDATED_COMMENT_NAME = "Barbara";

    private static final String UPDATED_COMMENT_TEXT = "Bla bla bla";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepositoryImpl repo;


    @Test
    @DisplayName("загружать один комментарий по id")
    void test_findById() {
        Optional<Comment> actualOpt = repo.findById(FIRST_COMMENT_ID);
        Comment expected = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @DisplayName("сохранять комментарий")
    void test_save() {
        Book book = em.find(Book.class, BOOK_ID);
        Comment comment = new Comment(null, UPDATED_COMMENT_NAME, UPDATED_COMMENT_TEXT, book);
        repo.save(comment);
        assertThat(comment.getId()).isNotNull();

        Comment actual = em.find(Comment.class, comment.getId());
        assertThat(actual).isNotNull()
                .matches(a -> a.getName().equals(UPDATED_COMMENT_NAME))
                .matches(a -> a.getText().equals(UPDATED_COMMENT_TEXT));
    }

    @Test
    @DisplayName("удалять комментарий по id")
    void test_deleteById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();
        em.detach(comment);

        repo.deleteById(FIRST_COMMENT_ID);
        Comment deleted = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deleted).isNull();
    }
}