package ru.otus.spring.jdbclibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbclibrary.dao.GenreDao;
import ru.otus.spring.jdbclibrary.domain.Genre;
import ru.otus.spring.jdbclibrary.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre getGenreById(Long genreId) {
        return genreDao.getById(genreId);
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.create(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void deleteGenreById(Long genreId) {
        genreDao.deleteById(genreId);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }
}
