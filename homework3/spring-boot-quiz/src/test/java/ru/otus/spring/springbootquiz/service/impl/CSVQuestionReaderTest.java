package ru.otus.spring.springbootquiz.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.springbootquiz.config.AppConfig;
import ru.otus.spring.springbootquiz.config.QuizProperties;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.service.QuestionReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = {QuizProperties.class})
@ContextConfiguration(classes = {CSVQuestionReader.class, QuestionParserImpl.class, LocalizationServiceImpl.class, AppConfig.class})
@TestPropertySource({"classpath:application.properties"})
@DisplayName("Загрузчик вопросов из файла")
class CSVQuestionReaderTest {

    @Autowired
    QuestionReader reader;

    @Test
    @DisplayName("успешно загрузил вопросы")
    void test_readQuestions_successful() {
        List<Question> questions = reader.readQuestions();
        assertEquals(3, questions.size());
        questions.forEach(System.out::println);
    }
}