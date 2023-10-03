package ru.otus.spring.springshellquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.springshellquiz.config.QuizProperties;

@EnableConfigurationProperties(value = {QuizProperties.class})
@SpringBootApplication
public class SpringShellQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShellQuizApplication.class, args);
	}

}
