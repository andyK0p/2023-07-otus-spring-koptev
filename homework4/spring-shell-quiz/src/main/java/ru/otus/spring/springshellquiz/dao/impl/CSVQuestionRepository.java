package ru.otus.spring.springshellquiz.dao.impl;


import org.springframework.stereotype.Component;
import ru.otus.spring.springshellquiz.dao.QuestionRepository;
import ru.otus.spring.springshellquiz.domain.Question;
import ru.otus.spring.springshellquiz.service.QuestionReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CSVQuestionRepository implements QuestionRepository {

    private final QuestionReader questionReader;

    private final List<Question> questions;

    public CSVQuestionRepository(QuestionReader questionReader) {
        this.questionReader = questionReader;
        questions = new ArrayList<>();
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public Optional<Question> getQuestionById(int id) {
        return questions
                .stream()
                .filter(q -> id == q.getId())
                .findFirst();
    }

    @Override
    public int getQuestionsCount() {
        return questions.size();
    }

    @Override
    public void addQuestions(List<Question> questions) {
        if (!questions.isEmpty()) {
            this.questions.addAll(questions);
        }
    }

    @Override
    public void loadQuestions() {
        List<Question> questionList = questionReader.readQuestions();
        addQuestions(questionList);
    }

    @Override
    public void clearAll() {
        questions.clear();
    }
}
