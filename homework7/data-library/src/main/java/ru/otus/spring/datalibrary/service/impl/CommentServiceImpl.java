package ru.otus.spring.datalibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.datalibrary.data.entity.Comment;
import ru.otus.spring.datalibrary.data.repository.CommentRepository;
import ru.otus.spring.datalibrary.exception.NotFoundException;
import ru.otus.spring.datalibrary.service.CommentService;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;


    @Override
    @Transactional(readOnly = true)
    public Comment getById(Long commentId) {
        return repo.findById(commentId).orElseThrow(NotFoundException::new);
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
