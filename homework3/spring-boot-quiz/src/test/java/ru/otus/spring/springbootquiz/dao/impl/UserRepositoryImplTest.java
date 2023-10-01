package ru.otus.spring.springbootquiz.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.springbootquiz.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Хранилище User")
class UserRepositoryImplTest {

    private UserRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new UserRepositoryImpl();
        Arrays.asList(
                        new User(1,"Firstname1", "Lastname1"),
                        new User(2,"Firstname2","Lastname2"))
                .forEach(user -> repo.createUser(user));
    }

    @Test
    @DisplayName("возвращает всех пользователей")
    void test_getUsers() {
        List<User> expectedList = Arrays.asList(
                new User(1,"Firstname1", "Lastname1"),
                new User(2,"Firstname2","Lastname2"));
        List<User> actualList = repo.getUsers();
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("возвращает пользователя по id")
    void test_getUserById() {
        int id = 1;
        User expected = new User(id,"Firstname1", "Lastname1");
        User actual = repo.getUserById(id).get();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("создает пользователя")
    void test_createUser() {
        int index = repo.getUsers().size();
        User user = new User(index + 1,"Firstname3", "Lastname3");
        User actual = repo.createUser(user);
        assertEquals(user, actual);
        assertEquals(index + 1, repo.getUsers().size());
    }

    @Test
    @DisplayName("удаляет пользователя")
    void test_removeUser() {
        int before = repo.getUsers().size();
        repo.removeUser(2);
        int after = repo.getUsers().size();
        assertEquals(before - 1, after);
    }

    @Test
    @DisplayName("модифицирует существующего пользователя")
    void test_saveUser() {
        int id = 2;
        User user = new User(id,"Abra", "Cadabra");
        repo.save(user);
        User modified = repo.getUserById(id).get();
        assertEquals(user, modified);
    }
}