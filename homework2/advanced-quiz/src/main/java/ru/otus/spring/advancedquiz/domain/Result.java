package ru.otus.spring.advancedquiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int id;

    private User user;

    private int correctAnswers;

    private Boolean passed;
}
