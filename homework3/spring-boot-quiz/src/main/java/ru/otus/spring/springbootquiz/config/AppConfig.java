package ru.otus.spring.springbootquiz.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    private final String filePath;

    public AppConfig(QuizProperties properties) {
        this.filePath = properties.getRepoPath().get(properties.getCurrent().toString());
    }
}
