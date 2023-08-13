package ru.otus.spring.simplequiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {

    private int id;

    private int questionId;

    private String text;

    public Answer(int id, int questionId, String text) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
    }
}
