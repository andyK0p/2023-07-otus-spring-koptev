package ru.otus.spring.actuatorlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.actuatorlibrary.data.entity.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}
