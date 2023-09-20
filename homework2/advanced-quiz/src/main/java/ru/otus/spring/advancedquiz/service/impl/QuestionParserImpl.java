package ru.otus.spring.advancedquiz.service.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.service.QuestionParser;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionParserImpl implements QuestionParser {
    @Override
    public Question parseQuestion(String line) {
        int index = 0;
        Question question = new Question();
        List<Answer> answerList = new ArrayList<>();
        for (String data : line.split(";")) {
            Answer answer = null;
            switch (index) {
                case 0 -> question.setId(Integer.parseInt(data));
                case 1 -> question.setText(data);
                case 2, 3, 4 -> answer = new Answer(index - 1, question.getId(), data);
                case 5 -> question.setCorrectAnswerId(Integer.parseInt(data));
                default -> System.out.println("Unexpected data at index: " + index);
            }
            if (answer != null) {
                answerList.add(answer);
            }
            index++;
        }
        question.setAnswers(answerList);
        return question;
    }
}
