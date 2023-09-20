package ru.otus.spring.advancedquiz.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.exception.IncorrectInputException;
import ru.otus.spring.advancedquiz.exception.QuizException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс утилит")
class QuizUtilsTest {

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
        assertTrue(QuizUtils.parseYesNoInput("y"));
        assertFalse(QuizUtils.parseYesNoInput("n"));
    }

    @Test
    @DisplayName("падает, когда пользователь ввел неправильные символы")
    void test_parseYesNoInput_whenIncorrectInput_thenFailed() {
        String message = "Incorrect input! Must be 'y' or 'n'.";
        String empty = "";
        Exception exception = assertThrows(IncorrectInputException.class, () -> QuizUtils.parseYesNoInput(empty));
        assertEquals(message, exception.getMessage());

        String blank = " ";
        exception = assertThrows(IncorrectInputException.class, () -> QuizUtils.parseYesNoInput(blank));
        assertEquals(message, exception.getMessage());

        String moreThen1Char = "abracadabra";
        exception = assertThrows(IncorrectInputException.class, () -> QuizUtils.parseYesNoInput(moreThen1Char));
        assertEquals(message, exception.getMessage());

        String wrongChar = "z";
        exception = assertThrows(IncorrectInputException.class, () -> QuizUtils.parseYesNoInput(wrongChar));
        assertEquals(message, exception.getMessage());
    }
}