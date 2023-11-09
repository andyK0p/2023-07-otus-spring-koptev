package ru.otus.spring.jpalibrary.dao.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jpalibrary.dao.entity.Book;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий Комментариев должен")
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    private static final int COMMENTS_COUNT = 2;

    private static final long BOOK_ID = 3L;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final String UPDATED_COMMENT_NAME = "Barbara";

    private static final String UPDATED_COMMENT_TEXT = "Bla bla bla";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepositoryImpl repo;

    @Test
    @DisplayName("загружать все комментарии для книги")
    void test_findAllByBookId() {
        List<CommentOutputDto> comments = repo.findAllByBookId(BOOK_ID);
        assertThat(comments).isNotNull().hasSize(COMMENTS_COUNT);
    }

    @Test
    @DisplayName("загружать один комментарий по id")
    void test_findById() {
        Optional<Comment> actualOpt = repo.findById(FIRST_COMMENT_ID);
        Comment expected = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualOpt).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @DisplayName("загружать DTO комментария по id")
    void test_findCommentDtoById() {
        Optional<CommentOutputDto> actualOpt = repo.findCommentDtoById(FIRST_COMMENT_ID);
        Comment expected = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualOpt).isPresent().get().matches(dto -> dto.getId().equals(expected.getId()));

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
    @DisplayName("обновлять текст комментария")
    void test_updateCommentText() {
        Book book = em.find(Book.class, BOOK_ID);
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        Comment copy = new Comment(comment.getId(), comment.getName(), comment.getText(), book);
        String oldText = copy.getText();
        em.detach(comment);

        copy.setText(UPDATED_COMMENT_TEXT);
        repo.save(copy);

        Comment updated = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(updated.getText()).isNotEqualTo(oldText);
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