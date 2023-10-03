package ru.otus.spring.springshellquiz.tests.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.springshellquiz.dao.impl.ResultRepositoryImpl;
import ru.otus.spring.springshellquiz.domain.Result;
import ru.otus.spring.springshellquiz.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Хранилище Result")
class ResultRepositoryImplTest {

    private ResultRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new ResultRepositoryImpl();
        Arrays.asList(
                        new Result(1, new User(1, "Firstname1", "Lastname1"), 4, true),
                        new Result(2, new User(2,"Firstname2","Lastname2"),2,false))
                .forEach(result -> repo.createResult(result));
    }

    @Test
    @DisplayName("возвращает все результаты")
    void test_getResults() {
        List<Result> expectedList = Arrays.asList(
                new Result(1, new User(1, "Firstname1", "Lastname1"), 4, true),
                new Result(2, new User(2,"Firstname2","Lastname2"),2,false));
        List<Result> actualList = repo.getResults();
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("возвращает результат по Id")
    void test_getResultById() {
        int id = 1;
        Result expected = new Result(id, new User(id,"Firstname1", "Lastname1"), 4, true);
        Result actual = repo.getResultById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("возвращает результат по Id полбзователя")
    void getResultByUserId() {
        int id = 2;
        Result expected = new Result(id, new User(id,"Firstname2", "Lastname2"), 2, false);
        Result actual = repo.getResultByUserId(id).get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("создает новый результат")
    void createResult() {
        int index = repo.getResults().size();
        Result user = new Result(index + 1, new User(index + 1,"Firstname3", "Lastname3"),5,true);
        Result actual = repo.createResult(user);
        assertEquals(user, actual);
        assertEquals(index + 1, repo.getResults().size());
    }

    @Test
    @DisplayName("удаляет результат")
    void test_deleteResult() {
        int before = repo.getResults().size();
        repo.deleteResult(2);
        int after = repo.getResults().size();
        assertEquals(before - 1, after);
    }

    @Test
    @DisplayName("модифицирует существующий результат")
    void test_saveResult() {
        int id = 2;
        Result result = new Result(id, new User(id,"Firstname2", "Lastname2"), 5,true);
        repo.save(result);
        Result modified = repo.getResultById(id).get();
        assertEquals(result, modified);
    }
}