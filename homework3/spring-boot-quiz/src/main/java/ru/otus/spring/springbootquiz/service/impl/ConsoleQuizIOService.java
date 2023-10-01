package ru.otus.spring.springbootquiz.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.domain.Result;
import ru.otus.spring.springbootquiz.service.LocalizationService;
import ru.otus.spring.springbootquiz.service.QuizIOService;
import ru.otus.spring.springbootquiz.util.QuizUtils;

import java.util.List;
import java.util.Scanner;

@Service
public class ConsoleQuizIOService implements QuizIOService {

    private final LocalizationService localizationService;

    public ConsoleQuizIOService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public void printWelcomeMessage() {
        printText(localizationService.getMessage("greeting"));
    }

    @Override
    public void printQuestion(String question) {
        printText(question);
        print(localizationService.getMessage("answer") + " ");
    }

    @Override
    public void printAllQuestionsWithAnswers(List<Question> questions) {
        printText(localizationService.getMessage("allAnswers"));
        questions.forEach(q -> {
            printText(localizationService.getMessage("questionWithAnswersAndCorrectAnswer",
                    q.getId(), q.getText(), QuizUtils.answersAsString(q.getAnswers()), q.getCorrectAnswerId()));
        });
    }

    @Override
    public void printQuizResult(Result result) {
        String fullName = result.getUser().getFirstName() + " " + result.getUser().getLastName();
        printText(localizationService.getMessage("congrats", fullName));
        printText(localizationService.getMessage("youHave", result.getCorrectAnswers()));
        printText(localizationService.getMessage("yourResult", resultAsText(result.getPassed())));
    }

    @Override
    public void printQuizError(String message) {
        printText(localizationService.getMessage("quizError", message));
    }

    @Override
    public void printStartedMessage() {
        printText(localizationService.getMessage("started"));
        printText(localizationService.getMessage("hereAreQuestions"));
    }

    @Override
    public void printFinishedMessage() {
        printText(localizationService.getMessage("finished"));
    }

    @Override
    public void askRepeatQuiz() {
        print(localizationService.getMessage("repeat"));
    }

    @Override
    public void askShowCorrectAnswers() {
        print(localizationService.getMessage("showAll"));
    }

    @Override
    public String readUserName() {
        print(localizationService.getMessage("enterName"));
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
        return result ? localizationService.getMessage("passed") : localizationService.getMessage("failed");
    }
}
