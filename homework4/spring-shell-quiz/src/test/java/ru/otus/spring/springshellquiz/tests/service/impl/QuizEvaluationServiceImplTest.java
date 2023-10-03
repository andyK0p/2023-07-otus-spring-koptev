package ru.otus.spring.springshellquiz.tests.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.springshellquiz.dao.ResultRepository;
import ru.otus.spring.springshellquiz.dao.impl.ResultRepositoryImpl;
import ru.otus.spring.springshellquiz.domain.Answer;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.domain.Result;
import ru.otus.spring.springshellquiz.domain.User;
import ru.otus.spring.springshellquiz.service.QuizEvaluationService;
import ru.otus.spring.springshellquiz.service.impl.QuizEvaluationServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс оценок")
class QuizEvaluationServiceImplTest {
    private static final int NUMBER_TO_PASS = 4;
    @MockBean
    private ResultRepository resultRepo;

    @Autowired
    @Qualifier("evaluationService")
    private QuizEvaluationService service;

    @Configuration
    static class TestQuizEvaluationServiceConfiguration {
        @Bean
        public QuizEvaluationService evaluationService(ResultRepository resultRepository) {
            return new QuizEvaluationServiceImpl(NUMBER_TO_PASS, resultRepository);
        }
    }

    @Test
    @DisplayName("оценивает правильность ответа")
    void test_evaluateAnswerIsCorrect() {
        List<Answer> answerList = Arrays.asList(
                new Answer(1, 1, "Test answer1"),
                new Answer(2, 1, "Test answer2"),
                new Answer(3, 1, "Test answer3"));
        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(3);
        question.setAnswers(answerList);

        boolean correct = service.evaluateAnswerIsCorrect(3, question);
        assertTrue(correct);
        correct = service.evaluateAnswerIsCorrect(1, question);
        assertFalse(correct);
    }

    @Test
    @DisplayName("оценивает результаты теста")
    void test_evaluateQuizResult() {
        User user = new User(1, "Firstname", "Lastname");
        Result expected = new Result(1, user, 5, true);

        when(resultRepo.createResult(any(Result.class))).thenReturn(expected);

        Result actual = service.evaluateQuizResult(user, 5);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("оценивает критерий успешного прохождения теста")
    void test_evaluatePassed() {
        int correctAnswers = 3;
        assertFalse(service.evaluatePassed(correctAnswers));
        correctAnswers = 4;
        assertTrue(service.evaluatePassed(correctAnswers));
    }
}