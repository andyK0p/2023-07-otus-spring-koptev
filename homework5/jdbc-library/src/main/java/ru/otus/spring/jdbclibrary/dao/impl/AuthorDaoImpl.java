package ru.otus.spring.jdbclibrary.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbclibrary.dao.AuthorDao;
import ru.otus.spring.jdbclibrary.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("SELECT id, full_name FROM authors WHERE id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public void create(Author author) {
        jdbc.update("INSERT INTO authors (full_name) VALUES (:fullName)",
                Map.of("fullName", author.getFullName()));
    }

    @Override
    public void update(Author author) {
        jdbc.update("UPDATE authors SET id = :id, full_name = :fullName WHERE id = :id",
                Map.of("id", author.getId(), "fullName", author.getFullName()));
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM authors WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT id, full_name FROM authors", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
