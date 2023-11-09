package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;

import java.util.List;

public interface CommentService {

    List<CommentOutputDto> getAllCommentsByBookId(Long bookId);

    Comment getById(Long commentId);

    CommentOutputDto getCommentById(Long commentId);

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteCommentById(Long commentId);
}
