package ru.otus.spring.datalibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.datalibrary.data.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {

}
