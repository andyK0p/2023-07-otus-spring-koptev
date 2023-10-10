package ru.otus.spring.jdbclibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.jdbclibrary.model.BookDto;
import ru.otus.spring.jdbclibrary.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {

    private final LibraryService libraryService;

    @ShellMethod(value = "List authors", key = {"a", "authors"})
    public void listAuthors() {
        libraryService.listAllAuthors().forEach(System.out::println);
    }

    @ShellMethod(value = "List genres", key = {"g", "genres"})
    public void listGenres() {
        libraryService.listAllGenres().forEach(System.out::println);
    }

    @ShellMethod(value = "List books", key = {"b", "books"})
    public void listBooks() {
        libraryService.listAllBooks().forEach(System.out::println);
    }

    //example: c "Java Puzzlers" 1 128 1
    @ShellMethod(value = "Create book", key = {"c", "create"})
    public String createBook(@ShellOption String title,
                         @ShellOption String authorId,
                         @ShellOption String pageCount,
                         @ShellOption String genreId) {
        BookDto dto = new BookDto();
        dto.setTitle(title);
        dto.setAuthorId(Long.parseLong(authorId));
        dto.setPageCount(Integer.parseInt(pageCount));
        dto.setGenreId(Long.parseLong(genreId));
        libraryService.addBookToLibrary(dto);
        return "New book has been added to library.";
    }

    //example: r 1
    @ShellMethod(value = "Read book by id", key = {"r", "read"})
    public void readBook(@ShellOption(defaultValue = "1") String bookId) {
        System.out.println(libraryService.getBookById(Long.parseLong(bookId)));
    }

    //example: u 1 "Java Concurrency in Practice" 1 342 1
    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String updateBook(@ShellOption String bookId,
                             @ShellOption String title,
                             @ShellOption String authorId,
                             @ShellOption String pageCount,
                             @ShellOption String genreId) {
        BookDto dto = new BookDto();
        dto.setBookId(Long.parseLong(bookId));
        dto.setTitle(title);
        dto.setAuthorId(Long.parseLong(authorId));
        dto.setPageCount(Integer.parseInt(pageCount));
        dto.setGenreId(Long.parseLong(genreId));
        libraryService.updateBook(dto);
        return "The book with id=" + bookId + " has been updated.";
    }

    //example: d 3
    @ShellMethod(value = "Create book by id", key = {"d", "delete"})
    public String deleteBook(@ShellOption String bookId) {
        libraryService.deleteBook(Long.parseLong(bookId));
        return "The book with id=" + bookId + " has been deleted.";
    }
}
