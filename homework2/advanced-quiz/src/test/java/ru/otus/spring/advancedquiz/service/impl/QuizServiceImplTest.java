package ru.otus.spring.advancedquiz.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.advancedquiz.dao.QuestionRepository;
import ru.otus.spring.advancedquiz.dao.UserRepository;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.domain.User;
import ru.otus.spring.advancedquiz.exception.QuizException;
import ru.otus.spring.advancedquiz.service.QuizEvaluationService;
import ru.otus.spring.advancedquiz.service.QuizIOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс QuizService")
class QuizServiceImplTest {
    @Mock
    private QuestionRepository questionRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private QuizIOService ioService;
    @Mock
    private QuizEvaluationService evaluationService;
    @InjectMocks
    private QuizServiceImpl quizService;

    private List<Question> expectedList;

    private static final int CORRECT_ANSWER = 3;

    @BeforeEach
    void testSetup() {
        List<Answer> answerList = Arrays.asList(
                new Answer(1, 1, "Test answer1"),
                new Answer(2, 1, "Test answer2"),
                new Answer(3, 1, "Test answer3"));
        expectedList = new ArrayList<>(1);
        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(CORRECT_ANSWER);
        question.setAnswers(answerList);
        expectedList.add(question);
    }

    @AfterEach
    void clear() {
        expectedList.clear();
    }

    @Test
    @DisplayName("успешно выполняет тест")
    void test_runQuiz_successful() {
        User testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("Test");
        testUser.setLastName("Username");
        int numOfCorrectAnswers = 1;
        Result testResult = new Result();
        testResult.setId(1);
        testResult.setUser(testUser);
        testResult.setCorrectAnswers(numOfCorrectAnswers);
        testResult.setPassed(true);

        when(ioService.readUserName()).thenReturn("Test Username");
        when(userRepo.createUser(any(User.class))).thenReturn(testUser);
        when(questionRepo.getQuestions()).thenReturn(expectedList);
        when(ioService.readAnswer()).thenReturn(CORRECT_ANSWER);
        when(evaluationService.evaluateAnswerIsCorrect(anyInt(), any(Question.class))).thenReturn(true);
        when(evaluationService.evaluateQuizResult(testUser, numOfCorrectAnswers)).thenReturn(testResult);
        when(ioService.readInput()).thenReturn("n");

        quizService.runQuiz();

        verify(ioService, times(1)).printWelcomeMessage();
        verify(ioService, times(1)).readUserName();
        verify(ioService, times(1)).printStartedMessage();
        verify(userRepo, times(1)).createUser(any(User.class));
        verify(questionRepo, times(1)).getQuestions();
        verify(ioService, times(1)).printQuestion(anyString());
        verify(ioService, times(1)).readAnswer();
        verify(evaluationService, times(1)).evaluateAnswerIsCorrect(anyInt(), any(Question.class));
        verify(evaluationService, times(1)).evaluateQuizResult(testUser, numOfCorrectAnswers);
        verify(ioService, times(1)).printQuizResult(testResult);
        verify(ioService, times(2)).readInput();
        verify(ioService, times(0)).printQuizError(anyString());
        verify(ioService, times(1)).printFinishedMessage();
    }

    @Test
    @DisplayName("падает, если вычисление результата вернуло null")
    void test_runQuiz_WhenNullUser_thenFailed() {
        User testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("Test");
        testUser.setLastName("Username");
        String error = "Quiz interrupted with message: Result returned null because provided user is null!";

        when(ioService.readUserName()).thenReturn("Test Username");
        when(userRepo.createUser(any())).thenReturn(testUser);
        when(questionRepo.getQuestions()).thenReturn(expectedList);
        when(ioService.readAnswer()).thenReturn(CORRECT_ANSWER);
        when(evaluationService.evaluateAnswerIsCorrect(anyInt(), any(Question.class))).thenReturn(true);
        when(evaluationService.evaluateQuizResult(any(User.class), anyInt())).thenReturn(null);

        quizService.runQuiz();

        verify(ioService, times(1)).printQuizError(error);
    }

    @Test
    @DisplayName("отвечает на вопросы теста успешно")
    void test_doAnswerQuestions_successful() throws QuizException {
        Question question = expectedList.get(0);

        when(questionRepo.getQuestions()).thenReturn(expectedList);
        when(ioService.readAnswer()).thenReturn(CORRECT_ANSWER);
        when(evaluationService.evaluateAnswerIsCorrect(CORRECT_ANSWER, question)).thenReturn(true);

        int expected = 1;
        int actual = quizService.doAnswerQuestions();

        assertEquals(expected, actual);
        assertDoesNotThrow(() -> { quizService.doAnswerQuestions(); });
    }
}