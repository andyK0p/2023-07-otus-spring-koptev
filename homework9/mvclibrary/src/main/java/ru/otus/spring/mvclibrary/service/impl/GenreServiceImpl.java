package ru.otus.spring.mvclibrary.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.mvclibrary.data.entity.Genre;
import ru.otus.spring.mvclibrary.data.repository.GenreRepository;
import ru.otus.spring.mvclibrary.dto.GenreDto;
import ru.otus.spring.mvclibrary.exception.NotFoundException;
import ru.otus.spring.mvclibrary.mappers.GenreMapper;
import ru.otus.spring.mvclibrary.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repo;

    @Override
    public List<GenreDto> getAllGenres() {
        return repo.findAll().stream().map(GenreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public GenreDto getGenreById(Long genreId) {
        return repo.findById(genreId).map(GenreMapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = GenreMapper.toDomainObject(genreDto);
        Genre added = repo.save(genre);
        return GenreMapper.toDto(added);
    }

    @Override
    public GenreDto updateGenre(GenreDto genreDto) {
        Genre genre = GenreMapper.toDomainObject(genreDto);
        Genre updated = repo.save(genre);
        return GenreMapper.toDto(updated);
    }

    @Override
    public void deleteGenreById(Long genreId) {
        repo.deleteById(genreId);
    }
}
