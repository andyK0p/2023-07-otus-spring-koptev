package ru.otus.spring.springbootquiz.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.spring.springbootquiz.service.LocaleProvider;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис локализации")
class LocalizationServiceImplTest {

    @Mock
    LocaleProvider localeProvider;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    LocalizationServiceImpl localizationService;

    @Test
    @DisplayName("успешно получает локализованное сообщение")
    void test_getMessage() {
        String message = "some localized message";
        Mockito.when(localeProvider.getCurrent()).thenReturn(Locale.US);
        Mockito.when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn(message);

        String actual = localizationService.getMessage("some key", "some args");
        assertEquals(message, actual);
    }
}