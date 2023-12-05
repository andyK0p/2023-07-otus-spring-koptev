package ru.otus.spring.actuatorlibrary.indicators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.actuatorlibrary.service.BookService;

@Component
@RequiredArgsConstructor
public class BooksCountIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        if (bookService.countBooks() > 0) {
            return Health.up().build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .build();
        }
    }
}
