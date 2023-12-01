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
import ru.otus.spring.ajaxlibrary.dto.BookDto;
import ru.otus.spring.ajaxlibrary.dto.GenreDto;
import ru.otus.spring.ajaxlibrary.dto.input.BookInputDto;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;
import ru.otus.spring.ajaxlibrary.mappers.BookMapper;
import ru.otus.spring.ajaxlibrary.service.AuthorService;
import ru.otus.spring.ajaxlibrary.service.BookService;
import ru.otus.spring.ajaxlibrary.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер книг должен ")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final Long BOOK_ID = 1L;

    private static final Long AUTHOR_ID = 1L;

    private static final Long GENRE_ID = 1L;

    private static final String MISSING_ID_ERROR = "Book Id must be specified for UPDATE!";

    private static final String NON_NULL_ID_ERROR = "Book Id must be null for CREATE!";

    private static final String NOT_FOUND_ERROR = "Requested entity is not present in database!";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("успешно выводить все книги")
    void getAllBooks() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        List<BookDto> books = List.of(
                new BookDto(1L, "Test Book 1", 123, author, genre),
                new BookDto(2L, "Test Book 2", 232, author, genre)
        );
        given(bookService.getAllBooks()).willReturn(books);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    @DisplayName("успешно выводить книгу")
    void getBookById() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223,
                new AuthorDto(AUTHOR_ID, "Test Author"), new GenreDto(GENRE_ID, "Test Genre"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(get("/api/books/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке получить книгу с id, которого нет в базе")
    void test_shouldReturnExceptionWhenNotFound() throws Exception {
        NotFoundException notFound = new NotFoundException();
        given(bookService.getBookById(BOOK_ID)).willThrow(notFound);

        mvc.perform(get("/api/books/" + BOOK_ID))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ERROR));
    }

    @Test
    @DisplayName("успешно добавлять книгу")
    void addNewBook() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        BookDto input = new BookDto(null, "Test Book", 223, author, genre);
        given(authorService.getAuthorById(AUTHOR_ID)).willReturn(author);
        given(genreService.getGenreById(GENRE_ID)).willReturn(genre);

        BookDto returned = new BookDto(BOOK_ID, "Test Book", 223, author, genre);
        given(bookService.createBook(input)).willReturn(returned);

        String request = mapper.writeValueAsString(BookMapper.toInputDto(input));
        String expected = mapper.writeValueAsString(returned);

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке создать книгу с непустым id")
    void test_shouldReturnExceptionWhenIdIsNotNull() throws Exception {
        BookInputDto book = new BookInputDto(BOOK_ID, "Test Book", 123, AUTHOR_ID, GENRE_ID);
        String request = mapper.writeValueAsString(book);

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NON_NULL_ID_ERROR));

        Mockito.verify(bookService, times(0)).createBook(any());
    }

    @Test
    @DisplayName("успешно обновлять книгу")
    void updateBook() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID, "Test Author 1");
        GenreDto genre = new GenreDto(GENRE_ID, "Test Genre 1");
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223, author, genre);
        given(authorService.getAuthorById(AUTHOR_ID)).willReturn(author);
        given(genreService.getGenreById(GENRE_ID)).willReturn(genre);
        given(bookService.updateBook(book)).willReturn(book);

        String request = mapper.writeValueAsString(BookMapper.toInputDto(book));
        String expected = mapper.writeValueAsString(book);

        mvc.perform(put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке обновить книгу с пустым id")
    void test_shouldReturnExceptionWhenIdIsNull() throws Exception {
        BookInputDto book = new BookInputDto(null, "Test Book", 123, AUTHOR_ID, GENRE_ID);
        String request = mapper.writeValueAsString(book);

        mvc.perform(put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MISSING_ID_ERROR));

        Mockito.verify(bookService, times(0)).updateBook(any());
    }

    @Test
    @DisplayName("успешно удалять книгу")
    void deleteBook() throws Exception {
        mvc.perform(delete("/api/books/" + BOOK_ID))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).deleteBookById(BOOK_ID);
    }
}