package ru.otus.spring.jpalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.jpalibrary.dao.entity.Comment;
import ru.otus.spring.jpalibrary.dao.repository.CommentRepository;
import ru.otus.spring.jpalibrary.dto.CommentOutputDto;
import ru.otus.spring.jpalibrary.exception.NotFoundException;
import ru.otus.spring.jpalibrary.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<CommentOutputDto> getAllCommentsByBookId(Long bookId) {
        return repo.findAllByBookId(bookId);
    }

    @Override
    public Comment getById(Long commentId) {
        return repo.findById(commentId).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentOutputDto getCommentById(Long commentId) {
        return repo.findCommentDtoById(commentId).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {
        repo.save(comment);
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        repo.save(comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId) {
        repo.deleteById(commentId);
    }
}
