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
import ru.otus.spring.dockerlibrary.dto.CommentDto;
import ru.otus.spring.dockerlibrary.dto.GenreDto;
import ru.otus.spring.dockerlibrary.dto.input.CommentInputDto;
import ru.otus.spring.dockerlibrary.service.BookService;
import ru.otus.spring.dockerlibrary.service.CommentService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер комментариев должен ")
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private static final Long BOOK_ID = 1L;

    private static final Long AUTHOR_ID = 2L;

    private static final Long GENRE_ID = 3L;

    private static final Long COMMENT_ID = 1L;

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("успешно выводить комментарии для книги")
    void test_listCommentsForBookPage() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        List<CommentDto> comments = List.of(new CommentDto(COMMENT_ID, "TEST_USER", "TEST_TEXT", book));
        given(commentService.getCommentsByBookId(BOOK_ID)).willReturn(comments);
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(get("/comments/book/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("TEST_USER")))
                .andExpect(content().string(containsString("TEST_TEXT")))
                .andExpect(model().attributeExists("book","comments"))
                .andExpect(view().name("comment/listComments"));
    }

    @Test
    @DisplayName("успешно открывать страницу редактирования комментария")
    void test_editCommentPage() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        CommentDto comment = new CommentDto(COMMENT_ID, "TEST_USER", "TEST_TEXT", book);
        given(commentService.getCommentById(COMMENT_ID)).willReturn(comment);

        mvc.perform(get("/comments/" + COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("TEST_USER")))
                .andExpect(content().string(containsString("TEST_TEXT")))
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("comment/editComment"));
    }

    @Test
    @DisplayName("успешно открывать страницу создания нового комментария")
    void test_newCommentPage() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(get("/comments/book/" + BOOK_ID + "/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(model().attributeExists("newComment", "book"))
                .andExpect(view().name("comment/newComment"));
    }

    @Test
    @DisplayName("успешно добавлять комментарий")
    void test_addComment() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(post("/comments/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("newComment",
                                new CommentInputDto(null,"TEST_USER", "TEST_TEXT", BOOK_ID))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/book/" + BOOK_ID));

        verify(commentService,times(1)).addComment(any());
    }

    @Test
    @DisplayName("успешно обновлять комментарий")
    void test_updateComment() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        mvc.perform(patch("/comments/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("comment",
                                new CommentInputDto(COMMENT_ID,"TEST_USER", "TEST_TEXT", BOOK_ID))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/book/" + BOOK_ID));

        verify(commentService,times(1)).updateComment(any());
    }

    @Test
    @DisplayName("успешно удалять комментарий")
    void test_deleteComment() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "TEST BOOK",10,
                new AuthorDto(AUTHOR_ID,"TEST AUTHOR"), new GenreDto(GENRE_ID, "TEST GENRE"));
        CommentDto comment = new CommentDto(COMMENT_ID, "TEST_USER", "TEST_TEXT", book);
        given(commentService.getCommentById(COMMENT_ID)).willReturn(comment);

        mvc.perform(delete("/comments/" + COMMENT_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/comments/book/" + BOOK_ID));

        verify(commentService, times(1)).deleteCommentById(COMMENT_ID);
    }
}