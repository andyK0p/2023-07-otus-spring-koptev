package ru.otus.spring.jdbclibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private long id;

    private String title;

    private Author author;

    private int pageCount;

    private Genre genre;
}
