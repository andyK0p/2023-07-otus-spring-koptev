package ru.otus.spring.springshellquiz.tests.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.spring.springshellquiz.dao.QuestionRepository;
import ru.otus.spring.springshellquiz.dao.UserRepository;
import ru.otus.spring.springshellquiz.domain.Answer;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.domain.Result;
import ru.otus.spring.springshellquiz.domain.User;
import ru.otus.spring.springshellquiz.exception.QuizException;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuizEvaluationService;
import ru.otus.spring.springshellquiz.service.QuizIOService;
import ru.otus.spring.springshellquiz.service.QuizService;
import ru.otus.spring.springshellquiz.service.impl.QuizServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Класс QuizService")
class QuizServiceImplTest {
    private static final int CORRECT_ANSWER = 3;

    @MockBean
    private QuestionRepository questionRepo;
    @MockBean
    private UserRepository userRepo;
    @MockBean
    private QuizIOService ioService;
    @MockBean
    private QuizEvaluationService evaluationService;
    @MockBean
    private LocalizationService localizationService;
    @Autowired
    private QuizService quizService;

    private List<Question> expectedList;


    @TestConfiguration
    static class TestQuizIOServiceConfiguration {

        @Bean
        public QuizService quizService(QuestionRepository questionRepository,
                                       UserRepository userRepository,
                                       QuizEvaluationService evaluationService,
                                       QuizIOService ioService,
                                       LocalizationService localizationService) {
            return new QuizServiceImpl(questionRepository, userRepository, evaluationService, ioService,
                    localizationService);
        }
    }

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
        when(localizationService.getMessage(anyString())).thenReturn("some message");
        when(localizationService.getMessage(anyString(), anyString())).thenReturn(error);

        quizService.runQuiz();

        verify(ioService, times(1)).printQuizError(error);
    }
}