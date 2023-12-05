package ru.otus.spring.actuatorlibrary.data.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.actuatorlibrary.data.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(Long id);
}
