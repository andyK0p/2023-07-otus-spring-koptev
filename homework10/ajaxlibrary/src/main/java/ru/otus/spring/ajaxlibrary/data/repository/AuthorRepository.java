package ru.otus.spring.ajaxlibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.ajaxlibrary.data.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Long> {

}
