package ru.otus.spring.simplequiz.service.impl;

import ru.otus.spring.simplequiz.dao.QuestionRepository;
import ru.otus.spring.simplequiz.service.QuizService;


public class QuizServiceImpl implements QuizService {
    private final QuestionRepository questionRepository;

    public QuizServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void init() {
        questionRepository.loadQuestions();
    }

    @Override
    public void printQuestions() {
        System.out.println("Welcome to Java Quiz!");
        System.out.println("Here are a few questions:\n");
        questionRepository.getQuestions().forEach(q -> {
            System.out.println("Question #" + q.getId() + ": " + q.getText());
            System.out.println("Possible answers: ");
            q.getAnswers().forEach(answer -> System.out.println("\t" + answer.getId() + "): " + answer.getText()));
            System.out.println("Correct answer is: " + q.getCorrectAnswerId() + "\n");
        });
        System.out.println("Quiz is finished!");
    }
}
