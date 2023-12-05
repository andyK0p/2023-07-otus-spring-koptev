package ru.otus.spring.actuatorlibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.actuatorlibrary.data.entity.Comment;
import ru.otus.spring.actuatorlibrary.data.repository.CommentRepository;
import ru.otus.spring.actuatorlibrary.dto.CommentDto;
import ru.otus.spring.actuatorlibrary.exception.NotFoundException;
import ru.otus.spring.actuatorlibrary.mappers.CommentMapper;
import ru.otus.spring.actuatorlibrary.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;

    @Override
    @Transactional
    public List<CommentDto> getCommentsByBookId(Long bookId) {
        return repo.findByBookId(bookId).stream().map(CommentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long commentId) {
        return repo.findById(commentId).map(CommentMapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = CommentMapper.toDomainObject(commentDto);
        Comment added = repo.save(comment);
        return CommentMapper.toDto(added);
    }

    @Override
    @Transactional
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = CommentMapper.toDomainObject(commentDto);
        Comment updated = repo.save(comment);
        return CommentMapper.toDto(updated);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        repo.deleteById(commentId);
    }
}
