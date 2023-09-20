package ru.otus.spring.advancedquiz.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.service.QuestionReader;
import ru.otus.spring.advancedquiz.service.impl.CSVQuestionReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CSVQuestionReader.class, QuestionParserImpl.class})
@TestPropertySource({"classpath:application-test.properties"})
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