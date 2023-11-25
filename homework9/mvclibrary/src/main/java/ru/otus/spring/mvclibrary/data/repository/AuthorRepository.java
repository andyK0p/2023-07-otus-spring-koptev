package ru.otus.spring.mvclibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.mvclibrary.data.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {

}
