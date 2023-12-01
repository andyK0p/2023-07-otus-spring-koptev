package ru.otus.spring.ajaxlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.ajaxlibrary.dto.GenreDto;
import ru.otus.spring.ajaxlibrary.exception.MustNotBeNullException;
import ru.otus.spring.ajaxlibrary.exception.NonNullException;
import ru.otus.spring.ajaxlibrary.service.GenreService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    //http://localhost/api/genres
    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    //http://localhost/api/genres/1
    @GetMapping("/api/genres/{id}")
    public GenreDto getGenreById(@PathVariable("id") Long genreId) {
        return genreService.getGenreById(genreId);
    }

    //http://localhost/api/genres
    @PostMapping("/api/genres")
    public GenreDto addNewGenre(@RequestBody GenreDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new NonNullException("Genre Id");
        }
        return genreService.addGenre(dto);
    }

    //http://localhost/api/genres
    @PutMapping("/api/genres")
    public GenreDto updateGenre(@RequestBody GenreDto dto) {
        if (Objects.isNull(dto.getId())) {
            throw new MustNotBeNullException("Genre Id");
        }
        return genreService.updateGenre(dto);
    }

    //http://localhost/api/genres/1
    @DeleteMapping("/api/genres/{id}")
    public void deleteGenre(@PathVariable("id") Long genreId) {
        genreService.deleteGenreById(genreId);
    }
}
