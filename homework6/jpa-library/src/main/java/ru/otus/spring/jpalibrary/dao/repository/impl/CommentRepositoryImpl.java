package ru.otus.spring.jpalibrary.dao.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dao.repository.CommentRepository;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<CommentOutputDto> findAllByBookId(Long bookId) {
        TypedQuery<CommentOutputDto> query = em.createQuery("select" +
                " new ru.otus.spring.jpalibrary.dto.CommentOutputDto(c.id, c.name, c.text, c.book.id, c.book.title)" +
                        " from Comment c where c.book.id = :bookId", CommentOutputDto.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Optional<CommentOutputDto> findCommentDtoById(Long id) {
        TypedQuery<CommentOutputDto> query = em.createQuery("select" +
                " new ru.otus.spring.jpalibrary.dto.CommentOutputDto(c.id, c.name, c.text, c.book.id, c.book.title)" +
                "from Comment c where c.id = :id", CommentOutputDto.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id).ifPresent(em::remove);
    }
}
