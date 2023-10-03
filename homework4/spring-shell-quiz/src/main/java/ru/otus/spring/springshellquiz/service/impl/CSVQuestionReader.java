package ru.otus.spring.springshellquiz.service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.spring.springshellquiz.config.provider.QuestionStorageProvider;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuestionParser;
import ru.otus.spring.springshellquiz.service.QuestionReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVQuestionReader implements QuestionReader {
    private final String fileName;

    private final QuestionParser parser;

    private final LocalizationService localizationService;

    public CSVQuestionReader(QuestionParser parser,
                             LocalizationService localizationService,
                             QuestionStorageProvider questionStorageProvider) {
        this.parser = parser;
        this.fileName = questionStorageProvider.getFilePath();
        this.localizationService = localizationService;
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
            System.out.println(localizationService.getMessage("csvReadError", ex.getMessage()));
            ex.printStackTrace();
        }
        return questions;
    }
}
