package ru.otus.spring.datalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.datalibrary.data.entity.Book;
import ru.otus.spring.datalibrary.data.entity.Comment;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookOutputDto {

    private Long id;

    private String title;

    private Integer pageCount;

    private String author;

    private String genre;

    private String comments;

    public static BookOutputDto fromDomain(Book book) {
        return new BookOutputDto(
                book.getId(),
                book.getTitle(),
                book.getPageCount(),
                book.getAuthor().getFullName(),
                book.getGenre().getName(),
                book.getComments().stream().map(CommentAuthorMapper::mapToAuthorAndText)
                        .collect(Collectors.joining(","))
        );
    }

    private static class CommentAuthorMapper {
        public static String mapToAuthorAndText(Comment comment) {
            return "\"@" + comment.getName() + ": " + comment.getText() + "\"";
        }
    }
}
