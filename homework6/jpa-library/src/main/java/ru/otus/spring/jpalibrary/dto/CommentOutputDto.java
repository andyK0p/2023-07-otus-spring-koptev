package ru.otus.spring.jpalibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentOutputDto {

    private Long id;

    private String name;

    private String text;

    private Long bookId;

    private String bookTitle;
}
