package ru.otus.spring.springshellquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.springshellquiz.dao.ResultRepository;
import ru.otus.spring.springshellquiz.domain.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ResultRepositoryImpl implements ResultRepository {

    private final List<Result> results;

    public ResultRepositoryImpl() {
        this.results = new ArrayList<>();
    }

    @Override
    public List<Result> getResults() {
        return results;
    }

    @Override
    public Optional<Result> getResultById(int id) {
        return results
                .stream()
                .filter(result -> result.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Result> getResultByUserId(int userId) {
        return results
                .stream()
                .filter(result -> result.getUser().getId() == userId)
                .findFirst();
    }

    @Override
    public Result createResult(Result result) {
        if (result != null) {
            result.setId(results.size() + 1);
            results.add(result);
        }
        return result;
    }

    @Override
    public void deleteResult(int id) {
        results.remove(id - 1);
    }

    @Override
    public void save(Result result) {
        if (result != null && result.getId() > 0) {
            deleteResult(result.getId());
            int index = result.getId() - 1;
            results.add(index, result);
        }
    }
}
