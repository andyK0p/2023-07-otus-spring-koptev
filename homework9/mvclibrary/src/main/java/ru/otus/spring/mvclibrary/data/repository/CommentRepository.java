package ru.otus.spring.mvclibrary.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.mvclibrary.data.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);

}
