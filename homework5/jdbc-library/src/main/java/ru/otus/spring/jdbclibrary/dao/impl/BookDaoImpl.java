package ru.otus.spring.jdbclibrary.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbclibrary.dao.BookDao;
import ru.otus.spring.jdbclibrary.domain.Author;
import ru.otus.spring.jdbclibrary.domain.Book;
import ru.otus.spring.jdbclibrary.domain.Genre;
import ru.otus.spring.jdbclibrary.model.BookDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject(
                "SELECT b.id, b.title, a.id author_id, a.full_name author_name, b.page_count, " +
                        "g.id genre_id, g.name genre_name FROM books b " +
                        "LEFT JOIN authors a ON b.author_id = a.id " +
                        "LEFT JOIN genres g ON b.genre_id = g.id " +
                        "WHERE b.id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public void create(BookDto book) {
        jdbc.update("INSERT INTO books (title, author_id, page_count, genre_id) " +
                "VALUES (:title, :authorId, :pageCount, :genreId)",
                Map.of("title", book.getTitle(), "authorId", book.getAuthorId(),
                        "pageCount", book.getPageCount(),
                        "genreId", book.getGenreId()));
    }

    @Override
    public void update(BookDto book) {
        jdbc.update("UPDATE books SET id = :id, title = :title, author_id = :authorId, " +
                "page_count = :pageCount, genre_id = :genreId where id = :id",
                Map.of("id", book.getBookId(), "title", book.getTitle(), "authorId", book.getAuthorId(),
                        "pageCount", book.getPageCount(), "genreId", book.getGenreId())
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM books where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT b.id, b.title, a.id author_id, a.full_name author_name, b.page_count, " +
                "g.id genre_id, g.name genre_name FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.id " +
                "LEFT JOIN genres g ON b.genre_id = g.id", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"));
            int pageCount = rs.getInt("page_count");
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(id, title, author, pageCount, genre);
        }
    }
}
