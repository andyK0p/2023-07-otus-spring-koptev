package ru.otus.spring.springshellquiz.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.springshellquiz.dao.QuestionRepository;
import ru.otus.spring.springshellquiz.dao.UserRepository;
import ru.otus.spring.springshellquiz.domain.Result;
import ru.otus.spring.springshellquiz.domain.User;
import ru.otus.spring.springshellquiz.exception.QuizException;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuizEvaluationService;
import ru.otus.spring.springshellquiz.service.QuizIOService;
import ru.otus.spring.springshellquiz.service.QuizService;

import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.otus.spring.springshellquiz.util.QuizUtils.answersAsString;
import static ru.otus.spring.springshellquiz.util.QuizUtils.parseYesNoInput;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final QuizEvaluationService evaluationService;

    private final QuizIOService ioService;

    private final LocalizationService localizationService;

    public QuizServiceImpl(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizEvaluationService evaluationService,
                           QuizIOService ioService,
                           LocalizationService localizationService) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.evaluationService = evaluationService;
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public void runQuiz() {
        questionRepository.loadQuestions();
        ioService.printWelcomeMessage();
        boolean running = true;
        while (running) {
            try {
                String fullName = ioService.readUserName();
                User user = initQuizUser(fullName);
                ioService.printStartedMessage();
                int numOfCorrectAnswers = doAnswerQuestions();
                evaluateAndShowResult(user, numOfCorrectAnswers);
                showCorrectAnswersIfNeeded();
                running = repeatQuiz();
            } catch (QuizException e) {
                ioService.printQuizError(localizationService.getMessage("interrupted", e.getMessage()));
                return;
            }
        }
        ioService.printFinishedMessage();
    }

    private User initQuizUser(String fullName) throws QuizException {
        if (!fullName.isEmpty() && !fullName.isBlank() && fullName.contains(" ")) {
            String firstName = fullName.substring(0, fullName.indexOf(" "));
            String lastName = fullName.substring(fullName.indexOf(" ") + 1);
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            return userRepository.createUser(user);
        } else {
            throw new QuizException(localizationService.getMessage("notEmptyName"));
        }
    }

    @Override
    public int doAnswerQuestions() throws QuizException {
        try {
            AtomicInteger correctAnswers = new AtomicInteger();
            questionRepository.getQuestions().forEach(question -> {
                ioService.printQuestion(question.getText() + "\n" + answersAsString(question.getAnswers()));
                int answer = ioService.readAnswer();
                if (evaluationService.evaluateAnswerIsCorrect(answer, question)) {
                    correctAnswers.getAndIncrement();
                }
            });
            return correctAnswers.get();
        } catch (InputMismatchException e) {
            throw new QuizException(localizationService.getMessage("onlyDigits"));
        }
    }

    private void showCorrectAnswersIfNeeded() throws QuizException {
        ioService.askShowCorrectAnswers();
        if (parseYesNoInput(ioService.readInput(), localizationService.getMessage("incorrectInput"))) {
            ioService.printAllQuestionsWithAnswers(questionRepository.getQuestions());
        }
    }

    private boolean repeatQuiz() throws QuizException {
        ioService.askRepeatQuiz();
        return parseYesNoInput(ioService.readInput(), localizationService.getMessage("incorrectInput"));
    }

    private void evaluateAndShowResult(User user, int correctAnswers) throws QuizException {
        Result quizResult = evaluationService.evaluateQuizResult(user, correctAnswers);
        if (quizResult != null) {
            ioService.printQuizResult(quizResult);
        } else {
            throw new QuizException(localizationService.getMessage("nullUser"));
        }
    }
}
