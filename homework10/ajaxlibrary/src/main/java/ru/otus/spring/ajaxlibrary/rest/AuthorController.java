package ru.otus.spring.ajaxlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.ajaxlibrary.dto.AuthorDto;
import ru.otus.spring.ajaxlibrary.exception.MustNotBeNullException;
import ru.otus.spring.ajaxlibrary.exception.NonNullException;
import ru.otus.spring.ajaxlibrary.service.AuthorService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //http://localhost/api/authors
    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    //http://localhost/api/authors/1
    @GetMapping("/api/authors/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    //http://localhost/api/authors
    @PostMapping("/api/authors")
    public AuthorDto addNewAuthor(@RequestBody AuthorDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new NonNullException("Author Id");
        }
        return authorService.addAuthor(dto);
    }

    //http://localhost/api/authors
    @PutMapping("/api/authors")
    public AuthorDto updateAuthor(@RequestBody AuthorDto dto) {
        if (Objects.isNull(dto.getId())) {
            throw new MustNotBeNullException("Author Id");
        }
        return authorService.updateAuthor(dto);
    }

    //http://localhost/api/authors/1
    @DeleteMapping("/api/authors/{id}")
    public void deleteAuthor(@PathVariable("id") Long authorId) {
        authorService.deleteAuthorById(authorId);
    }
}
