package ru.otus.spring.datalibrary.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInputDto {

    private Long id;

    private String title;

    private Integer pageCount;

    private Long authorId;

    private Long genreId;
}
