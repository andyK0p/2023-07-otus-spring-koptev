package ru.otus.spring.ajaxlibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.ajaxlibrary.dto.CommentDto;
import ru.otus.spring.ajaxlibrary.dto.input.CommentInputDto;
import ru.otus.spring.ajaxlibrary.exception.MustNotBeNullException;
import ru.otus.spring.ajaxlibrary.exception.NonNullException;
import ru.otus.spring.ajaxlibrary.service.BookService;
import ru.otus.spring.ajaxlibrary.service.CommentService;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    //http://localhost/api/comments/book/3
    @GetMapping("/api/comments/book/{id}")
    public List<CommentDto> getCommentsByBookId(@PathVariable("id") Long bookId) {
        return commentService.getCommentsByBookId(bookId);
    }

    //http://localhost/api/comments/1
    @GetMapping("/api/comments/{id}")
    public CommentDto getCommentById(@PathVariable("id") Long commentId) {
        return commentService.getCommentById(commentId);
    }

    //http://localhost/api/comments
    @PostMapping("/api/comments")
    public CommentDto addNewComment(@RequestBody CommentInputDto inputDto) {
        if (Objects.nonNull(inputDto.getId())) {
            throw new NonNullException("Comment Id");
        }
        CommentDto dto = new CommentDto();
        dto.setName(inputDto.getName());
        dto.setText(inputDto.getText());
        dto.setBook(bookService.getBookById(inputDto.getBookId()));
        return commentService.addComment(dto);
    }

    //http://localhost/api/comments
    @PutMapping("/api/comments")
    public CommentDto updateComment(@RequestBody CommentInputDto inputDto) {
        if (Objects.isNull(inputDto.getId())) {
            throw new MustNotBeNullException("Comment Id");
        }
        CommentDto dto = new CommentDto();
        dto.setId(inputDto.getId());
        dto.setName(inputDto.getName());
        dto.setText(inputDto.getText());
        dto.setBook(bookService.getBookById(inputDto.getBookId()));
        return commentService.updateComment(dto);
    }

    //http://localhost/api/comments/1
    @DeleteMapping("/api/comments/{id}")
    public void deleteComment(@PathVariable("id") Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}
