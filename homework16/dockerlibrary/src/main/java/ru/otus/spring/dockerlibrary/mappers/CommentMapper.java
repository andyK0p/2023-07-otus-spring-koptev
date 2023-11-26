package ru.otus.spring.dockerlibrary.mappers;

import ru.otus.spring.dockerlibrary.data.entity.Comment;
import ru.otus.spring.dockerlibrary.dto.CommentDto;
import ru.otus.spring.dockerlibrary.dto.input.CommentInputDto;

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
