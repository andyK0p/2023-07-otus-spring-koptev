package ru.otus.spring.dockerlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.dockerlibrary.data.entity.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}
