package ru.otus.spring.datalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.datalibrary.data.entity.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentOutputDto {

    private Long id;

    private String name;

    private String text;

    private String bookId;

    private String bookTitle;

    public static CommentOutputDto fromDomain(Comment comment) {
        return new CommentOutputDto(
                comment.getId(),
                comment.getName(),
                comment.getText(),
                comment.getBook().getId().toString(),
                comment.getBook().getTitle()
        );
    }
}
