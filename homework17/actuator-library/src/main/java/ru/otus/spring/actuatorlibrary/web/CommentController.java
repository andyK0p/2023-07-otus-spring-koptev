package ru.otus.spring.actuatorlibrary.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.actuatorlibrary.dto.CommentDto;
import ru.otus.spring.actuatorlibrary.dto.input.CommentInputDto;
import ru.otus.spring.actuatorlibrary.mappers.CommentMapper;
import ru.otus.spring.actuatorlibrary.service.BookService;
import ru.otus.spring.actuatorlibrary.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comments/book/{id}")
    public String listCommentsForBookPage(@PathVariable("id") Long bookId, Model model) {
        List<CommentDto> comments = commentService.getCommentsByBookId(bookId);
        model.addAttribute("book", bookService.getBookById(bookId));
        model.addAttribute("comments", comments);
        return "comment/listComments";
    }

    @GetMapping("/comments/{id}")
    public String editCommentPage(@PathVariable("id") Long commentId, Model model) {
        CommentDto comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", CommentMapper.toInputDto(comment));
        return "comment/editComment";
    }

    @GetMapping("/comments/book/{id}/new")
    public String newCommentPage(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("newComment", new CommentInputDto());
        model.addAttribute("book", bookService.getBookById(bookId));
        return "comment/newComment";
    }

    @PostMapping("/comments/add")
    public String addComment(@ModelAttribute("newComment") CommentInputDto inputDto) {
        CommentDto dto = new CommentDto();
        dto.setName(inputDto.getName());
        dto.setText(inputDto.getText());
        dto.setBook(bookService.getBookById(inputDto.getBookId()));
        commentService.addComment(dto);
        return "redirect:/comments/book/" + inputDto.getBookId();
    }

    @PatchMapping("/comments/edit")
    public String updateComment(@ModelAttribute("comment") CommentInputDto inputDto) {
        CommentDto dto = new CommentDto();
        dto.setId(inputDto.getId());
        dto.setName(inputDto.getName());
        dto.setText(inputDto.getText());
        dto.setBook(bookService.getBookById(inputDto.getBookId()));
        commentService.updateComment(dto);
        return "redirect:/comments/book/" + inputDto.getBookId();
    }

    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable("id") Long commentId) {
        CommentDto dto = commentService.getCommentById(commentId);
        Long bookId = dto.getBook().getId();
        commentService.deleteCommentById(commentId);
        return "redirect:/comments/book/" + bookId;
    }
}
