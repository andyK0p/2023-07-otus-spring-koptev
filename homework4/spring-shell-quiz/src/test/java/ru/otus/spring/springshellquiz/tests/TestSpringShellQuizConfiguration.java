package ru.otus.spring.springshellquiz.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import ru.otus.spring.springshellquiz.config.QuizProperties;
import ru.otus.spring.springshellquiz.config.provider.QuestionStorageProvider;
import ru.otus.spring.springshellquiz.dao.QuestionRepository;
import ru.otus.spring.springshellquiz.dao.impl.CSVQuestionRepository;
import ru.otus.spring.springshellquiz.service.LocalizationService;
import ru.otus.spring.springshellquiz.service.QuestionParser;
import ru.otus.spring.springshellquiz.service.QuestionReader;
import ru.otus.spring.springshellquiz.service.impl.CSVQuestionReader;
import ru.otus.spring.springshellquiz.service.impl.LocalizationServiceImpl;
import ru.otus.spring.springshellquiz.service.impl.QuestionParserImpl;

@EnableConfigurationProperties(value = {QuizProperties.class})
@SpringBootConfiguration
public class TestSpringShellQuizConfiguration {

    @Autowired
    QuizProperties properties;

    @Autowired
    MessageSource messageSource;

    @Bean
    public QuestionStorageProvider storageProvider() {
        return new QuestionStorageProvider(properties);
    }

    @Bean
    public LocalizationService localizationService() {
        return new LocalizationServiceImpl(properties, messageSource);
    }

    @Bean
    public QuestionParser questionParser() {
        return new QuestionParserImpl(localizationService());
    }

    @Bean
    public QuestionReader questionReader() {
        return new CSVQuestionReader(questionParser(), localizationService(), storageProvider());
    }

    @Bean
    public QuestionRepository questionRepository() {
        return new CSVQuestionRepository(questionReader());
    }
}
