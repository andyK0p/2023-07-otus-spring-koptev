package ru.otus.spring.springbootquiz.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.springbootquiz.domain.Answer;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.domain.Result;
import ru.otus.spring.springbootquiz.domain.User;
import ru.otus.spring.springbootquiz.service.LocalizationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс консольного IO сервиса")
class ConsoleQuizIOServiceTest {
    @Mock
    LocalizationService localizationService;
    @InjectMocks
    private ConsoleQuizIOService ioService;
    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("печатает приветствие")
    void test_printWelcomeMessage() {
        String expected = "Welcome to Java Quiz!";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn(expected);

        ioService.printWelcomeMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает вопрос")
    void test_printQuestion() {
        String expected = "Test question\r\nAnswer:";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn("Answer:");

        ioService.printQuestion("Test question");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает вопросы с ответами")
    void test_printAllQuestionsWithAnswers() {
        String expected = "Here are all questions with answers:\r\nTest question1\nTest question2";
        List<Answer> answers1 = Arrays.asList(
                new Answer(1, 1, "Test answer1"),
                new Answer(2, 1, "Test answer2"),
                new Answer(3, 1, "Test answer3")
        );
        Question question1 = new Question(1, "Test question1", answers1, 1);

        Mockito.when(localizationService.getMessage(anyString())).thenReturn("Here are all questions with answers:");
        Mockito.when(localizationService.getMessage(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn("Test question1\nTest question2");

        ioService.printAllQuestionsWithAnswers(List.of(question1));
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает результаты теста")
    void test_printQuizResult() {
        User testUser = new User();
        testUser.setId(1);
        testUser.setFirstName("Firstname");
        testUser.setLastName("Lastname");

        Result testResult = new Result();
        testResult.setId(1);
        testResult.setUser(testUser);
        testResult.setCorrectAnswers(5);
        testResult.setPassed(true);

        String expected = "Congratulations, Firstname Lastname!\r\nYou have 5 correct answers.\r\nYour result is: PASSED!";
        Mockito.when(localizationService.getMessage(anyString(), eq("Firstname Lastname"))).thenReturn("Congratulations, Firstname Lastname!");
        Mockito.when(localizationService.getMessage(anyString(), anyInt())).thenReturn("You have 5 correct answers.");
        Mockito.when(localizationService.getMessage(eq("passed"))).thenReturn("PASSED");
        Mockito.when(localizationService.getMessage(anyString(), eq("PASSED"))).thenReturn("Your result is: PASSED!");

        ioService.printQuizResult(testResult);
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает ошибку")
    void test_printQuizError() {
        String expected = "QUIZ ERROR! Some test error happened!";
        Mockito.when(localizationService.getMessage(anyString(), anyString())).thenReturn(expected);
        ioService.printQuizError("Some test error happened!");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает начало теста")
    void printStartedMessage() {
        String expected = "Let's get started!\r\nHere are some questions:";
        Mockito.when(localizationService.getMessage(eq("started"))).thenReturn("Let's get started!");
        Mockito.when(localizationService.getMessage(eq("hereAreQuestions"))).thenReturn("Here are some questions:");

        ioService.printStartedMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает завершение теста")
    void test_printFinishedMessage() {
        String expected = "Quiz is finished! Good bye!";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn(expected);

        ioService.printFinishedMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("спрашивает повторить ли тест")
    void test_askRepeatQuiz() {
        String expected = "Repeat quiz? y/n:";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn(expected);

        ioService.askRepeatQuiz();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("спрашивает показать ли все вопросы с правильными ответами")
    void test_askShowCorrectAnswers() {
        String expected = "Show all questions with correct answers? y/n:";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn(expected);

        ioService.askShowCorrectAnswers();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("считывает имя пользователя")
    void test_readUserName() {
        String expected = "Username";
        String text = "Please, enter your full name:";
        Mockito.when(localizationService.getMessage(anyString())).thenReturn(text);

        setStringInput(expected);
        String actual = ioService.readUserName();
        assertEquals(expected, actual);
        assertEquals(text, getOutput());
    }

    @Test
    @DisplayName("считывает номер ответа")
    void test_readAnswer() {
        String expected = "11";
        setStringInput(expected);
        int actual = ioService.readAnswer();
        assertEquals(Integer.parseInt(expected), actual);
    }

    @Test
    @DisplayName("считывает текстовый ввод")
    void test_readInput() {
        String expected = "test";
        setStringInput(expected);
        String actual = ioService.readInput();
        assertEquals(expected, actual);
    }

    private void setStringInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString().trim();
    }
}