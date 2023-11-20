package ru.otus.spring.datalibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.datalibrary.data.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);
}
