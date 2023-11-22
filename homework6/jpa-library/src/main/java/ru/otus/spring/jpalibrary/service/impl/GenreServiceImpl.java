package ru.otus.spring.jpalibrary.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.jpalibrary.dao.entity.Genre;
import ru.otus.spring.jpalibrary.dao.repository.GenreRepository;
import ru.otus.spring.jpalibrary.exception.NotFoundException;
import ru.otus.spring.jpalibrary.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repo;

    @Override
    public List<Genre> getAllGenres() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(Long genreId) {
        return repo.findById(genreId).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void addGenre(Genre genre) {
        repo.save(genre);
    }

    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        repo.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenreById(Long genreId) {
        repo.deleteById(genreId);
    }
}
