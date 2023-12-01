package ru.otus.spring.ajaxlibrary.rest.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.ajaxlibrary.exception.MustNotBeNullException;
import ru.otus.spring.ajaxlibrary.exception.NonNullException;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;

@ControllerAdvice
public class CommonErrorHandler {

    @ExceptionHandler(MustNotBeNullException.class)
    public ResponseEntity<String> handleNoIdInUpdateRequest(MustNotBeNullException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NonNullException.class)
    public ResponseEntity<String> handleNonNullIdInCreateRequest(NonNullException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
