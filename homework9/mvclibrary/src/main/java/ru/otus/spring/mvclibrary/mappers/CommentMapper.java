package ru.otus.spring.mvclibrary.mappers;

import ru.otus.spring.mvclibrary.data.entity.Comment;
import ru.otus.spring.mvclibrary.dto.CommentDto;
import ru.otus.spring.mvclibrary.dto.input.CommentInputDto;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getName(), comment.getText(),
                BookMapper.toDto(comment.getBook()));
    }

    public static Comment toDomainObject(CommentDto dto) {
        return new Comment(dto.getId(), dto.getName(), dto.getText(), BookMapper.toDomainObject(dto.getBook()));
    }

    public static CommentInputDto toInputDto(CommentDto dto) {
        return new CommentInputDto(dto.getId(), dto.getName(), dto.getText(), dto.getBook().getId());
    }
}
