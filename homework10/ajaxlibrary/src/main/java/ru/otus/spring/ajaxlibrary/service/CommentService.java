package ru.otus.spring.ajaxlibrary.service;

import ru.otus.spring.ajaxlibrary.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsByBookId(Long bookId);

    CommentDto getCommentById(Long commentId);

    CommentDto addComment(CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    void deleteCommentById(Long commentId);
}
