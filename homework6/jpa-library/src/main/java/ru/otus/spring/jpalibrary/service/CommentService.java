package ru.otus.spring.jpalibrary.service;

import ru.otus.spring.jpalibrary.dao.entity.Comment;

public interface CommentService {

    Comment getById(Long commentId);

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteCommentById(Long commentId);
}
