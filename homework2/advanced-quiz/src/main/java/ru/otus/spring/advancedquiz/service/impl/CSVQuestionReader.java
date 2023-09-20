package ru.otus.spring.advancedquiz.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.service.QuestionParser;
import ru.otus.spring.advancedquiz.service.QuestionReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVQuestionReader implements QuestionReader {
    private final String fileName;

    private final QuestionParser parser;

    public CSVQuestionReader(@Value("${app.repo.path}") String fileName, QuestionParser parser) {
        this.fileName = fileName;
        this.parser = parser;
    }

    @Override
    public List<Question> readQuestions() {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                questions.add(parser.parseQuestion(line));
            }
        } catch (IOException ex) {
            System.out.println("Error while reading CSV file. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
        return questions;
    }
}
