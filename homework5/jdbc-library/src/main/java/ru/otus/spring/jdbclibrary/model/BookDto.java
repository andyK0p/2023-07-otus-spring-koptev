package ru.otus.spring.jdbclibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long bookId;

    private String title;

    private Long authorId;

    private Integer pageCount;

    private Long genreId;
}
