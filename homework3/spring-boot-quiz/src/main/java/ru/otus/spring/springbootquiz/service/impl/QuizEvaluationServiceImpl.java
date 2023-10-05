package ru.otus.spring.springbootquiz.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.springbootquiz.dao.ResultRepository;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.domain.Result;
import ru.otus.spring.springbootquiz.domain.User;
import ru.otus.spring.springbootquiz.service.QuizEvaluationService;

@Service
public class QuizEvaluationServiceImpl implements QuizEvaluationService {

    private final int numberToPass;

    private final ResultRepository resultRepository;

    public QuizEvaluationServiceImpl(@Value("${app.quiz.minimum-correct-amount}") int numberToPass,
                                     ResultRepository resultRepository) {
        this.numberToPass = numberToPass;
        this.resultRepository = resultRepository;
    }

    @Override
    public boolean evaluateAnswerIsCorrect(int answer, Question question) {
        return answer == question.getCorrectAnswerId();
    }

    @Override
    public Result evaluateQuizResult(User user, int numOfCorrectAnswers) {
        if (user == null) {
            return null;
        }
        Result result = new Result();
        result.setUser(user);
        result.setCorrectAnswers(numOfCorrectAnswers);
        result.setPassed(evaluatePassed(numOfCorrectAnswers));
        return resultRepository.createResult(result);
    }

    public boolean evaluatePassed(int numOfCorrectAnswers) {
        return numOfCorrectAnswers >= numberToPass;
    }


}
