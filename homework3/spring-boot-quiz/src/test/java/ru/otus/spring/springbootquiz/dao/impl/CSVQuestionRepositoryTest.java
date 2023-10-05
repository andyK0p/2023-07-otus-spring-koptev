package ru.otus.spring.springbootquiz.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.springbootquiz.config.provider.QuestionStorageProvider;
import ru.otus.spring.springbootquiz.config.QuizProperties;
import ru.otus.spring.springbootquiz.dao.QuestionRepository;
import ru.otus.spring.springbootquiz.domain.Answer;
import ru.otus.spring.springbootquiz.domain.Question;
import ru.otus.spring.springbootquiz.service.impl.CSVQuestionReader;
import ru.otus.spring.springbootquiz.service.impl.LocalizationServiceImpl;
import ru.otus.spring.springbootquiz.service.impl.QuestionParserImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = {QuizProperties.class})
@ContextConfiguration(classes = {CSVQuestionReader.class, CSVQuestionRepository.class, QuestionParserImpl.class, LocalizationServiceImpl.class, QuestionStorageProvider.class})
@TestPropertySource({"classpath:application.properties"})
@DisplayName("Хранилище Question")
class CSVQuestionRepositoryTest {
    @Autowired
    private QuestionRepository repo;

    @Test
    @DisplayName("успешно загружает вопросы")
    void test_getQuestions() {
        List<Question> loaded = repo.getQuestions();
        loaded.forEach(System.out::println);
        int elementAtIndex = 1;

        assertEquals(3, loaded.size());
        assertEquals(2, loaded.get(elementAtIndex).getId());
        assertEquals("Test question2", loaded.get(elementAtIndex).getText());
        assertEquals("Test answer2", loaded.get(elementAtIndex).getAnswers().get(elementAtIndex).getText());
        assertEquals(3, loaded.get(elementAtIndex).getCorrectAnswerId());
    }

    @Test
    void getQuestionById() {
        Question question = repo.getQuestionById(3).orElse(null);
        System.out.println(question);

        assertNotNull(question);
        assertEquals(3, question.getId());
        assertEquals("Test question3", question.getText());
        assertEquals("Test answer1", question.getAnswers().get(0).getText());
        assertEquals(2, question.getCorrectAnswerId());
    }

    @Test
    void getQuestionsCount() {
        int count = repo.getQuestionsCount();
        System.out.println("Questions COUNT: " + count);
        assertEquals(3, count);
    }

    @Test
    void addQuestions() {
        int correctId = 2;
        List<Answer> answerList = Arrays.asList(
                new Answer(1, 4, "Yet another test answer1"),
                new Answer(2, 4, "Yet another test answer2"),
                new Answer(3, 4, "Yet another test answer3"));
        List<Question> questions = List.of(new Question(4, "Test question4", answerList, correctId));
        int before = repo.getQuestionsCount();
        repo.addQuestions(questions);
        int after = repo.getQuestionsCount();

        assertEquals(before, after - 1);

        Question added = repo.getQuestionById(after).orElse(null);
        System.out.println("ADDED:\n" + added);

        assertNotNull(added);
        assertEquals(4, added.getId());
        assertEquals("Test question4", added.getText());
        assertEquals(answerList, added.getAnswers());
        assertEquals(correctId, added.getCorrectAnswerId());
    }

    @BeforeEach
    void setUp() {
        repo.loadQuestions();
    }

    @AfterEach
    void tearDown() {
        repo.clearAll();
    }
}