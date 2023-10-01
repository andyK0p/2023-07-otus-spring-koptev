package ru.otus.spring.springbootquiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.springbootquiz.config.AppConfig;
import ru.otus.spring.springbootquiz.config.QuizProperties;
import ru.otus.spring.springbootquiz.dao.impl.CSVQuestionRepository;
import ru.otus.spring.springbootquiz.service.impl.CSVQuestionReader;
import ru.otus.spring.springbootquiz.service.impl.LocalizationServiceImpl;
import ru.otus.spring.springbootquiz.service.impl.QuestionParserImpl;

@EnableConfigurationProperties(value = {QuizProperties.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CSVQuestionReader.class, CSVQuestionRepository.class, QuestionParserImpl.class, LocalizationServiceImpl.class, AppConfig.class})
@TestPropertySource({"classpath:application.properties"})
class SpringBootQuizApplicationTests {

	@Test
	void contextLoads() {
	}

}
