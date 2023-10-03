package ru.otus.spring.springshellquiz.tests.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.springshellquiz.domain.Answer;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuestionParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Парсер вопросов")
class QuestionParserImplTest {

    @Autowired
    private QuestionParser parser;

    @MockBean
    private LocalizationService localizationService;

    @Test
    @DisplayName("успешно парсит вопрос")
    void test_parseQuestion_successful() {
        String questionLine = "1;Test question1;Test answer1;Test answer2;Test answer3;1";
        List<Answer> answerList = Arrays.asList(
                new Answer(1, 1, "Test answer1"),
                new Answer(2, 1, "Test answer2"),
                new Answer(3, 1, "Test answer3"));
        Question expected = new Question(1, "Test question1", answerList, 1);

        Question actual = parser.parseQuestion(questionLine);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("не может распарсить вопрос, если в строке больше 5 элементов")
    void test_parseQuestions_when_questionLineMoreThanFiveElements_thenGetUnexpectedDataMessage() {
        String questionLine = "1;Test question1;Test answer1;Test answer2;Test answer3;1;unexpected";

        parser.parseQuestion(questionLine);

        verify(localizationService, times(1)).getMessage("unexpectedData", 6);
    }
}