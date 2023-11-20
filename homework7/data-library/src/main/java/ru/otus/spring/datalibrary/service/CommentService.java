package ru.otus.spring.datalibrary.service;

import ru.otus.spring.datalibrary.data.entity.Comment;

public interface CommentService {

    Comment getById(Long commentId);

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteCommentById(Long commentId);
}
