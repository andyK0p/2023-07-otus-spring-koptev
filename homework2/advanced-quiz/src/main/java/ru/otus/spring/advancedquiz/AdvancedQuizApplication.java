package ru.otus.spring.advancedquiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.advancedquiz.service.QuizService;

@Configuration
@ComponentScan(basePackages = {"ru.otus.spring.advancedquiz"})
public class AdvancedQuizApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AdvancedQuizApplication.class);
		QuizService quiz = context.getBean(QuizService.class);

		quiz.runQuiz();

		context.close();
	}
}
