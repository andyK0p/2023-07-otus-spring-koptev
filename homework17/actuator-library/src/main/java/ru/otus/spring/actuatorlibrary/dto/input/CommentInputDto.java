package ru.otus.spring.actuatorlibrary.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInputDto {

    private Long id;

    private String name;

    private String text;

    private Long bookId;
}
