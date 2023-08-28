package ru.otus.spring.advancedquiz.util;

import lombok.experimental.UtilityClass;
import ru.otus.spring.advancedquiz.domain.Answer;
import ru.otus.spring.advancedquiz.exception.IncorrectInputException;
import ru.otus.spring.advancedquiz.exception.QuizException;

import java.util.List;

@UtilityClass
public class QuizUtils {

    public static String answersAsString(List<Answer> answers) {
        StringBuilder str = new StringBuilder();
        answers.forEach(answer -> {
            str.append('\t');
            str.append(answer.getId());
            str.append("): ");
            str.append(answer.getText());
            str.append('\n');
        });
        return str.toString();
    }

    public static boolean parseInput(String input) throws QuizException {
        if (!input.isEmpty() && !input.isBlank() && input.length() == 1) {
            if (input.toLowerCase().charAt(0) == 'y') {
                return true;
            } else if (input.toLowerCase().charAt(0) == 'n') {
                return false;
            } else {
                throw new IncorrectInputException();
            }
        } else {
            throw new IncorrectInputException();
        }
    }
}
