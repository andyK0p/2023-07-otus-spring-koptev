package ru.otus.spring.mvclibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.mvclibrary.data.entity.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}
