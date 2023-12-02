package ru.otus.spring.ajaxlibrary.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookPageController {

    @GetMapping("/books")
    public String listBooksPage(Model model) {
        return "book/listBooks";
    }

    @GetMapping("/books/{id}")
    public String editBookPage(@PathVariable("id") Long bookId, Model model) {
        return "book/editBook";
    }

    @GetMapping("/books/new")
    public String newBookPage(Model model) {
        return "book/newBook";
    }
}
