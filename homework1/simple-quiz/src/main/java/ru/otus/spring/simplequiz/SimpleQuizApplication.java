package ru.otus.spring.simplequiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.simplequiz.service.QuizService;

public class SimpleQuizApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
		QuizService quiz = context.getBean(QuizService.class);

		quiz.runQuiz();

		context.close();
	}
}
