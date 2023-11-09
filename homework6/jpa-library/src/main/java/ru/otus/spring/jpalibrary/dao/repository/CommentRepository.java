package ru.otus.spring.jpalibrary.dao.repository;

import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<CommentOutputDto> findAllByBookId(Long bookId);

    Optional<Comment> findById(Long id);

    Optional<CommentOutputDto> findCommentDtoById(Long id);

    Comment save(Comment comment);

    void deleteById(Long id);
}
