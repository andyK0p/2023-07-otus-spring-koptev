package ru.otus.spring.simplequiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Question {
    private int id;
    private String text;
    private List<Answer> answers;
    private int correctAnswerId;
}
