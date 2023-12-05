package ru.otus.spring.actuatorlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.actuatorlibrary.data.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {

}
