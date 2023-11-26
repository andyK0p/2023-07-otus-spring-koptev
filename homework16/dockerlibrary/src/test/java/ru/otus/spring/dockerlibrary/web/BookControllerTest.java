package ru.otus.spring.dockerlibrary.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dockerlibrary.dto.AuthorDto;
import ru.otus.spring.dockerlibrary.dto.BookDto;
import ru.otus.spring.dockerlibrary.dto.GenreDto;
import ru.otus.spring.dockerlibrary.dto.input.BookInputDto;
import ru.otus.spring.dockerlibrary.service.AuthorService;
import ru.otus.spring.dockerlibrary.service.BookService;
import ru.otus.spring.dockerlibrary.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер книг должен ")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final Long BOOK_ID = 1L;

    private static final Long AUTHOR_ID = 2L;

    private static final Long GENRE_ID = 3L;

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @Test
    @DisplayName("успешно выводить все книги")
    void listBooksPage() throws Exception {
        List<BookDto> books = List.of(new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE")));
        given(bookService.getAllBooks()).willReturn(books);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("TEST BOOK")))
                .andExpect(content().string(containsString("TEST AUTHOR")))
                .andExpect(content().string(containsString("TEST GENRE")))
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("book/listBooks"));
    }

    @Test
    @DisplayName("успешно открывать страницу редактирования книги")
    void editBookPage() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(get("/books/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("TEST BOOK")))
                .andExpect(model().attributeExists("book", "authors", "genres"))
                .andExpect(view().name("book/editBook"));
    }

    @Test
    @DisplayName("успешно открывать страницу создания книги")
    void newBookPage() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        List<AuthorDto> authors = List.of(new AuthorDto(AUTHOR_ID,"TEST AUTHOR"));
        List<GenreDto> genres = List.of(new GenreDto(GENRE_ID, "TEST GENRE"));
        given(authorService.getAllAuthors()).willReturn(authors);
        given(genreService.getAllGenres()).willReturn(genres);

        mvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(model().attributeExists("newbook", "authors", "genres"))
                .andExpect(view().name("book/newBook"));
    }

    @Test
    @DisplayName("успешно добавлять книгу")
    void createBook() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID,"TEST AUTHOR");
        GenreDto genre = new GenreDto(GENRE_ID, "TEST GENRE");
        given(authorService.getAuthorById(AUTHOR_ID)).willReturn(author);
        given(genreService.getGenreById(GENRE_ID)).willReturn(genre);

        mvc.perform(post("/books/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("newbook",
                        new BookInputDto(null, "TEST BOOK", 10, AUTHOR_ID, GENRE_ID))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).createBook(any());
    }

    @Test
    @DisplayName("успешно обновлять книгу")
    void updateBook() throws Exception {
        AuthorDto author = new AuthorDto(AUTHOR_ID,"TEST AUTHOR");
        GenreDto genre = new GenreDto(GENRE_ID, "TEST GENRE");
        given(authorService.getAuthorById(AUTHOR_ID)).willReturn(author);
        given(genreService.getGenreById(GENRE_ID)).willReturn(genre);

        mvc.perform(patch("/books/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("book",
                                new BookInputDto(BOOK_ID, "TEST BOOK", 10, AUTHOR_ID, GENRE_ID))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).updateBook(any());
    }

    @Test
    @DisplayName("успешно удалять книгу")
    void deleteBook() throws Exception {
        mvc.perform(delete("/books/" + BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).deleteBookById(BOOK_ID);
    }
}