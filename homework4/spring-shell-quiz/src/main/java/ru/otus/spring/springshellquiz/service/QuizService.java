package ru.otus.spring.springshellquiz.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.springshellquiz.exception.QuizException;

@Service
public interface QuizService {

    void runQuiz();

    int doAnswerQuestions() throws QuizException;
}
