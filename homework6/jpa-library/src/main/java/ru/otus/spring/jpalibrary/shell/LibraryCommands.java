package ru.otus.spring.jpalibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.jpalibrary.dto.BookDto;
import ru.otus.spring.jpalibrary.dto.CommentDto;
import ru.otus.spring.jpalibrary.service.LibraryService;


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

    //example: c 3
    @ShellMethod(value = "List comments for book", key = {"c", "comments"})
    public void listCommentsForBook(@ShellOption String bookId) {
        libraryService.listAllCommentsForBook(Long.parseLong(bookId)).forEach(System.out::println);
    }

    //example: cb "Java Puzzlers" 1 128 1
    @ShellMethod(value = "Create book", key = {"cb", "create-book"})
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

    //example: rb 1
    @ShellMethod(value = "Read book by id", key = {"rb", "read-book"})
    public void readBook(@ShellOption(defaultValue = "1") String bookId) {
        System.out.println(libraryService.getBookById(Long.parseLong(bookId)));
    }

    //example: ub 1 "Java Concurrency in Practice" 1 342 1
    @ShellMethod(value = "Update book", key = {"ub", "update-book"})
    public String updateBook(@ShellOption String bookId,
                             @ShellOption String title,
                             @ShellOption String authorId,
                             @ShellOption String pageCount,
                             @ShellOption String genreId) {
        BookDto dto = new BookDto();
        dto.setId(Long.parseLong(bookId));
        dto.setTitle(title);
        dto.setAuthorId(Long.parseLong(authorId));
        dto.setPageCount(Integer.parseInt(pageCount));
        dto.setGenreId(Long.parseLong(genreId));
        libraryService.updateBook(dto);
        return "The book with id=" + bookId + " has been updated.";
    }

    //example: db 2
    @ShellMethod(value = "Delete book by id", key = {"db", "delete-book"})
    public String deleteBook(@ShellOption String bookId) {
        libraryService.deleteBook(Long.parseLong(bookId));
        return "The book with id=" + bookId + " has been deleted.";
    }

    //example: cc "John" "Nice book!" 3
    @ShellMethod(value = "Create comment for book", key = {"cc", "create-comment"})
    public String createComment(@ShellOption String name,
                                @ShellOption String text,
                                @ShellOption String bookId) {
        CommentDto dto = new CommentDto();
        dto.setName(name);
        dto.setText(text);
        dto.setBookId(Long.parseLong(bookId));
        libraryService.addCommentToBook(dto);
        return "New comment has been added to book with id=" + bookId;
    }

    //example: uc 1 "Mary" "So interesting book!" 3
    @ShellMethod(value = "Update comment for book", key = {"uc", "update-comment"})
    public String updateComment(@ShellOption String commentId,
                                @ShellOption String name,
                                @ShellOption String text,
                                @ShellOption String bookId) {
        CommentDto dto = new CommentDto();
        dto.setId(Long.parseLong(commentId));
        dto.setName(name);
        dto.setText(text);
        dto.setBookId(Long.parseLong(bookId));
        libraryService.updateComment(dto);
        return "The comment with id=" + commentId + " has been updated.";
    }

    //example: rc 1
    @ShellMethod(value = "Read comment by id", key = {"rc", "read-comment"})
    public void readComment(@ShellOption(defaultValue = "1") String commentId) {
        System.out.println(libraryService.getCommentById(Long.parseLong(commentId)));
    }

    //example: dc 1 3
    @ShellMethod(value = "Delete comment by id", key = {"dc", "delete-comment"})
    public String deleteComment(@ShellOption String commentId,
                                @ShellOption String bookId) {
        CommentDto dto = new CommentDto();
        dto.setId(Long.parseLong(commentId));
        dto.setBookId(Long.parseLong(bookId));
        libraryService.deleteComment(dto);
        return "The comment with id=" + commentId + " has been deleted.";
    }
}
