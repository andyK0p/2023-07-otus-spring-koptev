package ru.otus.spring.springbootquiz.dao;

import ru.otus.spring.springbootquiz.domain.Result;

import java.util.List;
import java.util.Optional;

public interface ResultRepository {

    List<Result> getResults();

    Optional<Result> getResultById(int id);

    Optional<Result> getResultByUserId(int userId);

    Result createResult(Result result);

    void deleteResult(int id);

    void save(Result result);
}
