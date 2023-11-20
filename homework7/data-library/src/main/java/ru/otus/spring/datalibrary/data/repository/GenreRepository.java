package ru.otus.spring.datalibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.datalibrary.data.entity.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}
