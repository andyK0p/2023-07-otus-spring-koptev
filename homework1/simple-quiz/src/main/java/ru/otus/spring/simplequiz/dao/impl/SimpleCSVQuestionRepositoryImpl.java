package ru.otus.spring.simplequiz.dao.impl;

import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.simplequiz.dao.QuestionRepository;
import ru.otus.spring.simplequiz.domain.Answer;
import ru.otus.spring.simplequiz.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SimpleCSVQuestionRepositoryImpl implements QuestionRepository {
    private final List<Question> questions;

    private final String fileName;

    public SimpleCSVQuestionRepositoryImpl(String fileName) {
        questions = new ArrayList<>();
        this.fileName = fileName;
    }

    @Override
    public void loadQuestions() {
        questions.clear();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            String line;
            Scanner scanner;
            int index = 0;
            while ((line = br.readLine()) != null) {
                Question question = new Question();
                List<Answer> answerList = new ArrayList<>();
                scanner = new Scanner(line).useDelimiter(";");
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    Answer answer = null;
                    switch (index) {
                        case 0 -> question.setId(Integer.parseInt(data));
                        case 1 -> question.setText(data);
                        case 2, 3, 4 -> answer = new Answer(index - 1, question.getId(), data);
                        case 5 -> question.setCorrectAnswerId(Integer.parseInt(data));
                        default -> System.out.println("Unexpected data at index: " + index);
                    }
                    if (answer != null) {
                        answerList.add(answer);
                    }
                    index++;
                }
                question.setAnswers(answerList);
                questions.add(question);
                index = 0;
            }
        } catch (IOException ex) {
            System.out.println("Error while reading CSV file. Reason: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void importQuestions(List<Question> questions) {
        if (!questions.isEmpty()) {
            this.questions.addAll(questions);
        }
    }

    @Override
    public Optional<Question> getQuestionById(int id) {
        return questions
                .stream()
                .filter(q -> id == q.getId())
                .findFirst();
    }
}
