package ru.otus.spring.springshellquiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private int id;

    private String text;

    private List<Answer> answers;

    private int correctAnswerId;
}
