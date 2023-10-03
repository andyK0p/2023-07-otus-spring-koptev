package ru.otus.spring.springshellquiz.service.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.springshellquiz.domain.Answer;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuestionParser;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionParserImpl implements QuestionParser {

    private final LocalizationService localizationService;

    public QuestionParserImpl(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

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
                default -> System.out.println(localizationService.getMessage("unexpectedData", index));
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
