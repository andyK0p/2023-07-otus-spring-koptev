package ru.otus.spring.springbootquiz.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import ru.otus.spring.springbootquiz.service.LocaleProvider;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "app.quiz")
@Getter
@Setter
@ToString
public class QuizProperties implements LocaleProvider {

    private Locale locale;

    private Integer minimumCorrectAmount;

    private Map<String, String> repoPath;

    @ConstructorBinding
    public QuizProperties(Map<String, String> repoPath, Integer minimumCorrectAmount, String locale) {
        this.repoPath = repoPath;
        this.minimumCorrectAmount = minimumCorrectAmount;
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public Locale getCurrent() {
        return locale;
    }
}
