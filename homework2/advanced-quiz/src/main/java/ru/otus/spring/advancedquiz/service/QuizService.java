package ru.otus.spring.advancedquiz.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.advancedquiz.domain.User;

@Service
public interface QuizService {

    void runQuiz();
}
