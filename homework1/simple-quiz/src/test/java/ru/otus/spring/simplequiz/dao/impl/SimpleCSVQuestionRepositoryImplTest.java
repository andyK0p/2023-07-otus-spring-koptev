package ru.otus.spring.simplequiz.dao.impl;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.otus.spring.simplequiz.dao.QuestionRepository;
import ru.otus.spring.simplequiz.domain.Answer;
import ru.otus.spring.simplequiz.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SimpleCSVQuestionRepositoryImplTest {

    public static final String FILENAME = "test.csv";

    private final QuestionRepository repo;

    public SimpleCSVQuestionRepositoryImplTest() {
        this.repo = new SimpleCSVQuestionRepositoryImpl(FILENAME);
    }

    @AfterEach
    void clear() {
        repo.getQuestions().clear();
    }

    @Test
    void testLoadQuestions_successful() {
        List<Answer> answerList = new ArrayList<>(1);
        List<Question> expectedList = new ArrayList<>(1);
        Answer answer1 = new Answer(1, 1, "Test answer1");
        Answer answer2 = new Answer(2, 1, "Test answer2");
        Answer answer3 = new Answer(3, 1, "Test answer3");
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);

        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(1);
        question.setAnswers(answerList);
        expectedList.add(question);

        repo.loadQuestions();

        List<Question> actualList = repo.getQuestions();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getQuestionById_successful() {
        List<Answer> answerList = new ArrayList<>(1);
        List<Question> expectedList = new ArrayList<>(1);
        Answer answer = new Answer(1, 1, "Test answer");
        answerList.add(answer);
        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(1);
        question.setAnswers(answerList);
        expectedList.add(question);
        repo.importQuestions(expectedList);

        Optional<Question> optionalQuestion = repo.getQuestionById(1);
        assertTrue(optionalQuestion.isPresent());

        Question actual = optionalQuestion.get();
        assertEquals(question, actual);
    }


    @Test
    void importQuestions_successful() {
        List<Answer> answerList = new ArrayList<>(1);
        List<Question> expectedList = new ArrayList<>(1);
        Answer answer = new Answer(1, 1, "Test answer");
        answerList.add(answer);
        Question question = new Question();
        question.setId(1);
        question.setText("Test question");
        question.setCorrectAnswerId(1);
        question.setAnswers(answerList);
        expectedList.add(question);
        repo.importQuestions(expectedList);

        List<Question> actualList = repo.getQuestions();
        assertEquals(expectedList, actualList);
    }
}