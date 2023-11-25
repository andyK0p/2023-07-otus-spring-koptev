package ru.otus.spring.datalibrary.mappers;

import ru.otus.spring.datalibrary.data.entity.Comment;
import ru.otus.spring.datalibrary.dto.CommentDto;
import ru.otus.spring.datalibrary.dto.output.CommentOutputDto;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getName(), comment.getText(),
                BookMapper.toDto(comment.getBook()));
    }

    public static Comment toDomainObject(CommentDto dto) {
        return new Comment(dto.getId(), dto.getName(), dto.getText(), BookMapper.toDomainObject(dto.getBook()));
    }

    public static CommentOutputDto toOutputDto(CommentDto dto) {
        return new CommentOutputDto(
                dto.getId(),
                dto.getName(),
                dto.getText(),
                dto.getBook().getId().toString(),
                dto.getBook().getTitle()
        );
    }

//    public static CommentOutputDto toOutputDto(Comment comment) {
//        CommentDto dto = toDto(comment);
//        return new CommentOutputDto(
//                dto.getId(),
//                dto.getName(),
//                dto.getText(),
//                dto.getBook().getId().toString(),
//                dto.getBook().getTitle()
//        );
//    }
}
