package ru.otus.spring.advancedquiz.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.service.QuizIOService;

import java.util.List;
import java.util.Scanner;

@Service
public class ConsoleQuizIOService implements QuizIOService {

    @Override
    public void printWelcomeMessage() {
        printText("Welcome to Java Quiz!");
    }

    @Override
    public void printQuestion(String question) {
        printText(question);
        print("Answer: ");
    }

    @Override
    public void printAllQuestionsWithAnswers(List<String> questions) {
        printText("Here are all questions with answers:");
        questions.forEach(this::printText);
    }

    @Override
    public void printQuizResult(Result result) {
        printText("Congratulations, " + result.getUser().getFirstName() + " " + result.getUser().getLastName() + "!");
        printText("You have " + result.getCorrectAnswers() + " correct answers.");
        printText("Your result is: " + resultAsText(result.getPassed()) + "!");
    }

    @Override
    public void printQuizError(String message) {
        printText("QUIZ ERROR! " + message);
    }

    @Override
    public void printStartedMessage() {
        printText("Let's get started!");
        printText("Here are some questions: ");
    }

    @Override
    public void printFinishedMessage() {
        printText("Quiz is finished! Good bye!");
    }

    @Override
    public void askRepeatQuiz() {
        print("Repeat quiz? y/n: ");
    }

    @Override
    public void askShowCorrectAnswers() {
        print("Show all questions with correct answers? y/n: ");
    }

    @Override
    public String readUserName() {
        print("Please, enter your full name: ");
        return readInput();
    }

    @Override
    public int readAnswer() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    @Override
    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void printText(String text) {
        System.out.println(text);
    }

    private void print(String line) {
        System.out.print(line);
    }

    private String resultAsText(Boolean result) {
        return result ? "PASSED" : "FAILED";
    }
}
