package ru.otus.spring.advancedquiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.advancedquiz.util.QuizUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private int id;

    private String text;

    private List<Answer> answers;

    private int correctAnswerId;

    @Override
    public String toString() {
        return "Question #" + id +
                ": " + text + '\n' +
                "Possible answers:\n" +
                QuizUtils.answersAsString(answers) +
                "Correct answer is: " + correctAnswerId;
    }
}
