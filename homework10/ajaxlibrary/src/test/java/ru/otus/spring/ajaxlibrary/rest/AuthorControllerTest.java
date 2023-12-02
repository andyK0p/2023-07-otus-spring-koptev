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
import ru.otus.spring.ajaxlibrary.dto.AuthorDto;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;
import ru.otus.spring.ajaxlibrary.service.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер авторов должен ")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private static final Long AUTHOR_ID = 1L;
    private static final String MISSING_ID_ERROR = "Author Id must be specified for UPDATE!";

    private static final String NON_NULL_ID_ERROR = "Author Id must be null for CREATE!";
    private static final String NOT_FOUND_ERROR = "Requested entity is not present in database!";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("успешно выводить всех авторов")
    void test_getAllAuthors() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "Test Author 1"),
                new AuthorDto(2L, "Test Author 2"));
        given(authorService.getAllAuthors()).willReturn(authors);

        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));
    }

    @Test
    @DisplayName("успешно выводить автора")
    void test_getAuthorById() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        given(authorService.getAuthorById(AUTHOR_ID)).willReturn(author);

        mvc.perform(get("/api/authors/" + AUTHOR_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке получить автора с id, которого нет в базе")
    void test_shouldReturnExceptionWhenNotFound() throws Exception {
        NotFoundException notFound = new NotFoundException();
        given(authorService.getAuthorById(AUTHOR_ID)).willThrow(notFound);

        mvc.perform(get("/api/authors/" + AUTHOR_ID))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ERROR));
    }

    @Test
    @DisplayName("успешно добавлять автора")
    void test_addNewAuthor() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        AuthorDto input = new AuthorDto(null, "Test Author 1");
        given(authorService.addAuthor(input)).willReturn(author);
        String request = mapper.writeValueAsString(input);
        String expected = mapper.writeValueAsString(author);

        mvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке создать автора с непустым id")
    void test_shouldReturnExceptionWhenIdIsNotNull() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author");
        String expected = mapper.writeValueAsString(author);

        mvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NON_NULL_ID_ERROR));

        Mockito.verify(authorService, times(0)).addAuthor(author);
    }

    @Test
    @DisplayName("успешно обновлять автора")
    void test_updateAuthor() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        given(authorService.updateAuthor(author)).willReturn(author);
        String expected = mapper.writeValueAsString(author);

        mvc.perform(put("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке обновить автора с пустым id")
    void test_shouldReturnExceptionWhenIdIsNull() throws Exception {
        AuthorDto author = new AuthorDto(null, "Test Author 1");
        String expected = mapper.writeValueAsString(author);

        mvc.perform(put("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MISSING_ID_ERROR));

        Mockito.verify(authorService, times(0)).updateAuthor(author);
    }

    @Test
    @DisplayName("успешно удалять автора")
    void test_deleteAuthor() throws Exception {
        mvc.perform(delete("/api/authors/" + AUTHOR_ID))
                .andExpect(status().isOk());

        Mockito.verify(authorService, times(1)).deleteAuthorById(AUTHOR_ID);
    }
}