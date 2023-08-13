package ru.otus.spring.simplequiz.service.impl;

import ru.otus.spring.simplequiz.dao.QuestionRepository;
import ru.otus.spring.simplequiz.service.QuizService;

import java.util.Scanner;

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
        System.out.println("Here are a few questions:\n");
        questionRepository.getQuestions().forEach(q -> {
            System.out.println("Question #" + q.getId() + ": " + q.getText());
            System.out.println("Possible answers: ");
            q.getAnswers().forEach(answer -> System.out.println("\t" + answer.getId() + "): " + answer.getText()));
            System.out.println("Correct answer is: " + q.getCorrectAnswerId() + "\n");
        });
    }

    @Override
    public void runQuiz() {
        System.out.println("Welcome to Java Quiz!");
        System.out.print("Please, enter your full name: ");
        Scanner scanner = new Scanner(System.in);
        String fullName = scanner.nextLine();
        printQuestions();
        System.out.println("Congratulations, " + fullName + "!");
        System.out.println("Quiz is finished!");
    }
}
