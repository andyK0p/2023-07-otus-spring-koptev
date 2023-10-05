package ru.otus.spring.springbootquiz.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.springbootquiz.domain.Answer;
import ru.otus.spring.springbootquiz.exception.QuizException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс утилит")
class QuizUtilsTest {

    private static final String ERROR_MSG = "Incorrect input! Must be 'y' or 'n'.";

    @Test
    @DisplayName("приводит список ответов к форматированной строке")
    void answersAsString() {
        String expected = "\t1): Test answer1\n\t2): Test answer2\n\t3): Test answer3\n";
        List<Answer> answerList = Arrays.asList(
                new Answer(1, 1, "Test answer1"),
                new Answer(2, 1, "Test answer2"),
                new Answer(3, 1, "Test answer3"));
        assertEquals(expected, QuizUtils.answersAsString(answerList));
    }

    @Test
    @DisplayName("успешно парсит пользовательский ввод")
    void test_parseYesNoInput_successful() throws QuizException {
        assertTrue(QuizUtils.parseYesNoInput("y", ERROR_MSG));
        assertFalse(QuizUtils.parseYesNoInput("n", ERROR_MSG));
    }

    @Test
    @DisplayName("падает, когда пользователь ввел неправильные символы")
    void test_parseYesNoInput_whenIncorrectInput_thenFailed() {
        String empty = "";
        Exception exception = assertThrows(QuizException.class, () -> QuizUtils.parseYesNoInput(empty, ERROR_MSG));
        assertEquals(ERROR_MSG, exception.getMessage());

        String blank = " ";
        exception = assertThrows(QuizException.class, () -> QuizUtils.parseYesNoInput(blank, ERROR_MSG));
        assertEquals(ERROR_MSG, exception.getMessage());

        String moreThen1Char = "abracadabra";
        exception = assertThrows(QuizException.class, () -> QuizUtils.parseYesNoInput(moreThen1Char, ERROR_MSG));
        assertEquals(ERROR_MSG, exception.getMessage());

        String wrongChar = "z";
        exception = assertThrows(QuizException.class, () -> QuizUtils.parseYesNoInput(wrongChar, ERROR_MSG));
        assertEquals(ERROR_MSG, exception.getMessage());
    }
}