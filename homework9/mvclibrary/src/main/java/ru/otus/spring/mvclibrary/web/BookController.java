package ru.otus.spring.mvclibrary.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.mvclibrary.dto.BookDto;
import ru.otus.spring.mvclibrary.dto.input.BookInputDto;
import ru.otus.spring.mvclibrary.mappers.BookMapper;
import ru.otus.spring.mvclibrary.service.AuthorService;
import ru.otus.spring.mvclibrary.service.BookService;
import ru.otus.spring.mvclibrary.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/books")
    public String listBooksPage(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/listBooks";
    }

    @GetMapping("/books/{id}")
    public String editBookPage(@PathVariable("id") Long bookId, Model model) {
        BookDto book = bookService.getBookById(bookId);
        model.addAttribute("book", BookMapper.toInputDto(book));
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        return "book/editBook";
    }

    @GetMapping("/books/new")
    public String newBookPage(Model model) {
        model.addAttribute("newbook", new BookInputDto());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
        return "book/newBook";
    }

    @PostMapping("/books/add")
    public String createBook(@ModelAttribute("newbook") BookInputDto bookInput) {
        BookDto dto = new BookDto();
        dto.setTitle(bookInput.getTitle());
        dto.setPageCount(bookInput.getPageCount());
        dto.setAuthor(authorService.getAuthorById(bookInput.getAuthorId()));
        dto.setGenre(genreService.getGenreById(bookInput.getGenreId()));
        bookService.createBook(dto);
        return "redirect:/books";
    }

    @PatchMapping("/books/edit")
    public String updateBook(@ModelAttribute("book") BookInputDto bookInput) {
        BookDto dto = new BookDto();
        dto.setId(bookInput.getId());
        dto.setTitle(bookInput.getTitle());
        dto.setPageCount(bookInput.getPageCount());
        dto.setAuthor(authorService.getAuthorById(bookInput.getAuthorId()));
        dto.setGenre(genreService.getGenreById(bookInput.getGenreId()));
        bookService.updateBook(dto);
        return "redirect:/books";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);
        return "redirect:/books";
    }
}
