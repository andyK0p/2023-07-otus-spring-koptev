package ru.otus.spring.springshellquiz.tests.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.service.QuestionReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Загрузчик вопросов из файла")
class CSVQuestionReaderIntegrationTest {

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