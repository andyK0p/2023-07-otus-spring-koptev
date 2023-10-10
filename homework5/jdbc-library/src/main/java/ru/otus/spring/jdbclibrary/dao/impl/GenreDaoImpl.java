package ru.otus.spring.jdbclibrary.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbclibrary.dao.GenreDao;
import ru.otus.spring.jdbclibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("SELECT id, name FROM genres WHERE id = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public void create(Genre genre) {
        jdbc.update("INSERT INTO genres (name) VALUES (:name)",
                Map.of("name", genre.getName()));
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("UPDATE genres SET id = :id, name = :name WHERE id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM genres WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT id, name FROM genres", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
