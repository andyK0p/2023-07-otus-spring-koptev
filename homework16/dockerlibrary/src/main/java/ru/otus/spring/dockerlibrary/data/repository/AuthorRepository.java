package ru.otus.spring.dockerlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.dockerlibrary.data.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {

}
