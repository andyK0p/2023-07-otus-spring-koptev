package ru.otus.spring.ajaxlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.ajaxlibrary.data.entity.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

}
