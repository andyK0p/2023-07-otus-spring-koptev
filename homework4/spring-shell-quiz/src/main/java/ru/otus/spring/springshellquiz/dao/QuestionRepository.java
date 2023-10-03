package ru.otus.spring.springshellquiz.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.springshellquiz.domain.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository {

    List<Question> getQuestions();

    Optional<Question> getQuestionById(int id);

    int getQuestionsCount();

    void addQuestions(List<Question> questions);

    void loadQuestions();

    void clearAll();
}
