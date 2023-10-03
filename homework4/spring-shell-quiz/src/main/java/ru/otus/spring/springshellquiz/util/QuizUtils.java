package ru.otus.spring.springshellquiz.util;

import lombok.experimental.UtilityClass;
import ru.otus.spring.springshellquiz.domain.Answer;
import ru.otus.spring.springshellquiz.exception.QuizException;

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

    public static boolean parseYesNoInput(String input, String errorMessage) throws QuizException {
        if (!input.isEmpty() && !input.isBlank() && input.length() == 1) {
            if (input.toLowerCase().charAt(0) == 'y') {
                return true;
            } else if (input.toLowerCase().charAt(0) == 'n') {
                return false;
            } else {
                throw new QuizException(errorMessage);
            }
        } else {
            throw new QuizException(errorMessage);
        }
    }
}
