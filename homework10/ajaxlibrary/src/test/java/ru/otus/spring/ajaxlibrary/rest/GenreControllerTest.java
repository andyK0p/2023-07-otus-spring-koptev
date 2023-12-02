package ru.otus.spring.ajaxlibrary.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.ajaxlibrary.dto.GenreDto;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;
import ru.otus.spring.ajaxlibrary.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер жанров должен ")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    private static final Long GENRE_ID = 1L;

    private static final String MISSING_ID_ERROR = "Genre Id must be specified for UPDATE!";

    private static final String NON_NULL_ID_ERROR = "Genre Id must be null for CREATE!";

    private static final String NOT_FOUND_ERROR = "Requested entity is not present in database!";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;


    @Test
    @DisplayName("успешно выводить все жанры")
    void getAllGenres() throws Exception {
        List<GenreDto> genres = List.of(new GenreDto(1L, "Test Genre 1"),
                new GenreDto(2L, "Test Genre 2"));
        given(genreService.getAllGenres()).willReturn(genres);

        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));
    }

    @Test
    @DisplayName("успешно выводить жанр")
    void getGenreById() throws Exception {
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        given(genreService.getGenreById(GENRE_ID)).willReturn(genre);

        mvc.perform(get("/api/genres/" + GENRE_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке получить жанр с id, которого нет в базе")
    void test_shouldReturnExceptionWhenNotFound() throws Exception {
        NotFoundException notFound = new NotFoundException();
        given(genreService.getGenreById(GENRE_ID)).willThrow(notFound);

        mvc.perform(get("/api/genres/" + GENRE_ID))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ERROR));
    }

    @Test
    @DisplayName("успешно добавлять жанр")
    void addNewGenre() throws Exception {
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        GenreDto input = new GenreDto(null, "Test Genre 1");
        given(genreService.addGenre(input)).willReturn(genre);
        String request = mapper.writeValueAsString(input);
        String expected = mapper.writeValueAsString(genre);

        mvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке создать жанр с непустым id")
    void test_shouldReturnExceptionWhenIdIsNotNull() throws Exception {
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre");
        String expected = mapper.writeValueAsString(genre);

        mvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NON_NULL_ID_ERROR));

        Mockito.verify(genreService, times(0)).addGenre(genre);
    }

    @Test
    @DisplayName("успешно обновлять жанр")
    void updateGenre() throws Exception {
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        given(genreService.updateGenre(genre)).willReturn(genre);
        String expected = mapper.writeValueAsString(genre);

        mvc.perform(put("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке обновить жанр с пустым id")
    void test_shouldReturnExceptionWhenIdIsNull() throws Exception {
        GenreDto genre = new GenreDto(null, "Test Genre 1");
        String expected = mapper.writeValueAsString(genre);

        mvc.perform(put("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MISSING_ID_ERROR));

        Mockito.verify(genreService, times(0)).updateGenre(genre);
    }

    @Test
    @DisplayName("успешно удалять жанр")
    void deleteGenre() throws Exception {
        mvc.perform(delete("/api/genres/" + GENRE_ID))
                .andExpect(status().isOk());

        Mockito.verify(genreService, times(1)).deleteGenreById(GENRE_ID);
    }
}