package ru.otus.spring.springshellquiz.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class QuizShellCommands {

    private final QuizIOService ioService;

    private final QuizService quizService;

    private int ageInYears;

    @PostConstruct
    public void init() {
        ioService.printUnderageCheckNotice();
    }

    @ShellMethod(value = "Underage check", key = {"c", "check"})
    public String check(@ShellOption(defaultValue = "0") String age) {
        this.ageInYears = Integer.parseInt(age);
        return ioService.getAfterAgeCheckMessage();
    }

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    @ShellMethodAvailability(value = "isQuizStartAllowed")
    public void start() {
        quizService.runQuiz();
    }

    private Availability isQuizStartAllowed() {
        return this.ageInYears >= 18 ?
                Availability.available() :
                Availability.unavailable(ioService.getAgeCheckFailed());
    }
}
