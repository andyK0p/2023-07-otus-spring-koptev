package ru.otus.spring.simplequiz.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.simplequiz.dao.QuestionRepository;
import ru.otus.spring.simplequiz.dao.impl.SimpleCSVQuestionRepositoryImpl;
import ru.otus.spring.simplequiz.domain.Answer;
import ru.otus.spring.simplequiz.domain.Question;
import ru.otus.spring.simplequiz.service.QuizService;

import java.util.ArrayList;
import java.util.List;

class QuizServiceImplTest {
    private QuestionRepository repo;
    private QuizService quizService;

    @BeforeEach
    void init() {
        repo = Mockito.mock(SimpleCSVQuestionRepositoryImpl.class);
        quizService = new QuizServiceImpl(repo);
    }

    @Test
    void test_init_successful() {
        quizService.init();
        Mockito.verify(repo).loadQuestions();
    }

    @Test()
    void printQuestions() {
        List<Answer> answerList = new ArrayList<>(1);
        List<Question> questionList = new ArrayList<>(1);
        Answer answer = new Answer(1, 1, "Test answer");
        answerList.add(answer);
        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(1);
        question.setAnswers(answerList);
        questionList.add(question);

        Mockito.when(repo.getQuestions()).thenReturn(questionList);
        quizService.printQuestions();
        Mockito.verify(repo).getQuestions();
    }
}