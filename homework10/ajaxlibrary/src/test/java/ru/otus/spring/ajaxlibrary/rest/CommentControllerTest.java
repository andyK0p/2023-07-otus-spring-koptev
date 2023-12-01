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
import ru.otus.spring.ajaxlibrary.dto.CommentDto;
import ru.otus.spring.ajaxlibrary.dto.GenreDto;
import ru.otus.spring.ajaxlibrary.dto.input.CommentInputDto;
import ru.otus.spring.ajaxlibrary.exception.NotFoundException;
import ru.otus.spring.ajaxlibrary.mappers.CommentMapper;
import ru.otus.spring.ajaxlibrary.service.BookService;
import ru.otus.spring.ajaxlibrary.service.CommentService;

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

@DisplayName("Контроллер комментариев должен ")
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private static final Long BOOK_ID = 1L;

    private static final Long COMMENT_ID = 1L;

    private static final String MISSING_ID_ERROR = "Comment Id must be specified for UPDATE!";

    private static final String NON_NULL_ID_ERROR = "Comment Id must be null for CREATE!";

    private static final String NOT_FOUND_ERROR = "Requested entity is not present in database!";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("успешно выводить все комментарии для книги")
    void test_getCommentsByBookId() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223,
                new AuthorDto(1L, "Test Author"), new GenreDto(1L, "Test Genre"));
        List<CommentDto> comments = List.of(
                new CommentDto(1L, "Test Name 1", "Test Text 1", book),
                new CommentDto(2L, "Test Name 2", "Test Text 2", book));
        given(commentService.getCommentsByBookId(BOOK_ID)).willReturn(comments);

        mvc.perform(get("/api/comments/book/" + BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(comments)));
    }

    @Test
    @DisplayName("успешно выводить комментарий по id")
    void test_getCommentById() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223,
                new AuthorDto(1L, "Test Author"), new GenreDto(1L, "Test Genre"));
        CommentDto comment = new CommentDto(COMMENT_ID, "Test Name", "Test Text", book);
        given(commentService.getCommentById(COMMENT_ID)).willReturn(comment);

        mvc.perform(get("/api/comments/" + COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(comment)));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке получить комментарий с id, которого нет в базе")
    void test_shouldReturnExceptionWhenNotFound() throws Exception {
        NotFoundException notFound = new NotFoundException();
        given(commentService.getCommentById(COMMENT_ID)).willThrow(notFound);

        mvc.perform(get("/api/comments/" + COMMENT_ID))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ERROR));
    }

    @Test
    @DisplayName("успешно добавлять комментарий")
    void test_addNewComment() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223,
                new AuthorDto(1L, "Test Author"), new GenreDto(1L, "Test Genre"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        CommentDto returned = new CommentDto(COMMENT_ID, "Test Name", "Test Text", book);
        String expected = mapper.writeValueAsString(returned);

        CommentDto input = new CommentDto(null, "Test Name", "Test Text", book);
        String request = mapper.writeValueAsString(CommentMapper.toInputDto(input));
        given(commentService.addComment(input)).willReturn(returned);

        mvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке создать комментарий с непустым id")
    void test_shouldReturnExceptionWhenIdIsNotNull() throws Exception {
        CommentInputDto comment = new CommentInputDto(COMMENT_ID, "Test Name", "Test Text", BOOK_ID);
        String request = mapper.writeValueAsString(comment);

        mvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NON_NULL_ID_ERROR));

        Mockito.verify(commentService, times(0)).addComment(any());
    }

    @Test
    @DisplayName("успешно обновлять комментарий")
    void test_updateComment() throws Exception {
        BookDto book = new BookDto(BOOK_ID, "Test Book", 223,
                new AuthorDto(1L, "Test Author"), new GenreDto(1L, "Test Genre"));
        given(bookService.getBookById(BOOK_ID)).willReturn(book);

        CommentDto comment = new CommentDto(COMMENT_ID, "Test Name", "Test Text", book);
        given(commentService.updateComment(comment)).willReturn(comment);

        String request = mapper.writeValueAsString(CommentMapper.toInputDto(comment));
        String expected = mapper.writeValueAsString(comment);

        mvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("вернуть ошибку при попытке обновить комментарий с пустым id")
    void test_shouldReturnExceptionWhenIdIsNull() throws Exception {
        CommentInputDto comment = new CommentInputDto(null, "Test Name", "Test Text", BOOK_ID);
        String request = mapper.writeValueAsString(comment);

        mvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MISSING_ID_ERROR));

        Mockito.verify(commentService, times(0)).updateComment(any());
    }

    @Test
    @DisplayName("успешно удалять комментарий")
    void test_deleteComment() throws Exception {
        mvc.perform(delete("/api/comments/" + COMMENT_ID))
                .andExpect(status().isOk());

        Mockito.verify(commentService, times(1)).deleteCommentById(COMMENT_ID);
    }
}