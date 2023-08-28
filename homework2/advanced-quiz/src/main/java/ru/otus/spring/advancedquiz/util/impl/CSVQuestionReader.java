package ru.otus.spring.advancedquiz.util.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.util.QuestionReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVQuestionReader implements QuestionReader {
    private final String fileName;

    public CSVQuestionReader(@Value("${app.repo.path}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> readQuestions() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                questions.add(prepareQuestion(line));
            }
        } catch (IOException ex) {
            System.out.println("Error while reading CSV file. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
        return questions;
    }

    private Question prepareQuestion(String line) {
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
