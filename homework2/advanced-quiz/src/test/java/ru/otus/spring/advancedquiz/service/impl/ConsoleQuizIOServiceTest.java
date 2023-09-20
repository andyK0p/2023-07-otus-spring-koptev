package ru.otus.spring.advancedquiz.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.domain.User;
import ru.otus.spring.advancedquiz.service.QuizIOService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс консольного IO сервиса")
class ConsoleQuizIOServiceTest {
    private QuizIOService ioService;
    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        ioService = new ConsoleQuizIOService();
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
        ioService.printWelcomeMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает вопрос")
    void test_printQuestion() {
        String expected = "Test question\r\nAnswer:";
        ioService.printQuestion("Test question");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает вопросы с ответами")
    void test_printAllQuestionsWithAnswers() {
        String expected = "Here are all questions with answers:\r\nTest question1\r\nTest question2";
        ioService.printAllQuestionsWithAnswers(Arrays.asList("Test question1", "Test question2"));
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
        ioService.printQuizResult(testResult);
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает ошибку")
    void test_printQuizError() {
        String expected = "QUIZ ERROR! Some test error happened!";
        ioService.printQuizError("Some test error happened!");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает начало теста")
    void printStartedMessage() {
        String expected = "Let's get started!\r\nHere are some questions:";
        ioService.printStartedMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("печатает завершение теста")
    void test_printFinishedMessage() {
        String expected = "Quiz is finished! Good bye!";
        ioService.printFinishedMessage();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("спрашивает повторить ли тест")
    void test_askRepeatQuiz() {
        String expected = "Repeat quiz? y/n:";
        ioService.askRepeatQuiz();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("спрашивает показать ли все вопросы с правильными ответами")
    void test_askShowCorrectAnswers() {
        String expected = "Show all questions with correct answers? y/n:";
        ioService.askShowCorrectAnswers();
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("считывает имя пользователя")
    void test_readUserName() {
        String expected = "Username";
        String text = "Please, enter your full name:";
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