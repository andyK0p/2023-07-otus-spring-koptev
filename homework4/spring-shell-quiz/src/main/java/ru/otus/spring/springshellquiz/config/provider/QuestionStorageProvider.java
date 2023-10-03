package ru.otus.spring.springshellquiz.config.provider;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.otus.spring.springshellquiz.config.QuizProperties;

@Component
@Getter
public class QuestionStorageProvider {

    private final String filePath;

    public QuestionStorageProvider(QuizProperties properties) {
        this.filePath = properties.getRepoPath().get(properties.getCurrent().toString());
    }
}
