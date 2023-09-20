package ru.otus.spring.advancedquiz.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.advancedquiz.dao.ResultRepository;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс оценок")
class QuizEvaluationServiceImplTest {
    private static final int NUMBER_TO_PASS = 4;
    @Mock
    private ResultRepository resultRepo;
    private QuizEvaluationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new QuizEvaluationServiceImpl(NUMBER_TO_PASS, resultRepo);
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