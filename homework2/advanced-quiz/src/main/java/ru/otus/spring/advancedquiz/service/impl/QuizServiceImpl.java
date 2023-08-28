package ru.otus.spring.advancedquiz.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.advancedquiz.dao.QuestionRepository;
import ru.otus.spring.advancedquiz.dao.UserRepository;
import ru.otus.spring.advancedquiz.domain.Question;
import ru.otus.spring.advancedquiz.domain.Result;
import ru.otus.spring.advancedquiz.domain.User;
import ru.otus.spring.advancedquiz.exception.QuizException;
import ru.otus.spring.advancedquiz.service.QuizEvaluationService;
import ru.otus.spring.advancedquiz.service.QuizIOService;
import ru.otus.spring.advancedquiz.service.QuizService;

import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.otus.spring.advancedquiz.util.QuizUtils.answersAsString;
import static ru.otus.spring.advancedquiz.util.QuizUtils.parseInput;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final QuizEvaluationService evaluationService;

    private final QuizIOService ioService;

    public QuizServiceImpl(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizEvaluationService evaluationService,
                           QuizIOService ioService) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.evaluationService = evaluationService;
        this.ioService = ioService;
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
                ioService.printQuizError("Quiz interrupted with message: " + e.getMessage());
                return;
            }
        }
        ioService.printFinishedMessage();
    }

    public User initQuizUser(String fullName) throws QuizException {
        if (!fullName.isEmpty() && !fullName.isBlank() && fullName.contains(" ")) {
            String firstName = fullName.substring(0, fullName.indexOf(" "));
            String lastName = fullName.substring(fullName.indexOf(" ") + 1);
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            return userRepository.createUser(user);
        } else {
            throw new QuizException("Full name must not be empty or blank " +
                    "and must contain at least 1 space character!");
        }
    }

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
            throw new QuizException("You have entered the wrong character! Only digits allowed!");
        }
    }

    private void showCorrectAnswersIfNeeded() throws QuizException {
        ioService.askShowCorrectAnswers();
        if (parseInput(ioService.readInput())) {
            ioService.printAllQuestionsWithAnswers(
                    questionRepository.getQuestions()
                            .stream()
                            .map(Question::toString)
                            .toList()
            );
        }
    }

    private boolean repeatQuiz() throws QuizException {
        ioService.askRepeatQuiz();
        return parseInput(ioService.readInput());
    }

    private void evaluateAndShowResult(User user, int correctAnswers) throws QuizException {
        Result quizResult = evaluationService.evaluateQuizResult(user, correctAnswers);
        if (quizResult != null) {
            ioService.printQuizResult(quizResult);
        } else {
            throw new QuizException("Result returned null because provided user is null!");
        }
    }
}
