package ru.otus.spring.springbootquiz.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class QuizRunner implements ApplicationRunner {

    private final QuizService quizService;

    public QuizRunner(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        quizService.runQuiz();
    }
}
