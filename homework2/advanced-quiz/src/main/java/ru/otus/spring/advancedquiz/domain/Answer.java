package ru.otus.spring.advancedquiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private int id;

    private int questionId;

    private String text;
}
