package ru.otus.spring.jpalibrary.dao.repository;

import ru.otus.spring.jpalibrary.dao.entity.Comment;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
