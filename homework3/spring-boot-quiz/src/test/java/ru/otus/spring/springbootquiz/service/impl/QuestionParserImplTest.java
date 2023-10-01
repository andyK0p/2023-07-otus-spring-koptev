package ru.otus.spring.springbootquiz.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.springbootquiz.domain.Answer;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.service.LocalizationService;
import ru.otus.spring.springbootquiz.service.QuestionParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Парсер вопросов")
class QuestionParserImplTest {

    private QuestionParser parser;

    @Mock
    private LocalizationService localizationService;

    @BeforeEach
    void setUp() {
        parser = new QuestionParserImpl(localizationService);
    }

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
}