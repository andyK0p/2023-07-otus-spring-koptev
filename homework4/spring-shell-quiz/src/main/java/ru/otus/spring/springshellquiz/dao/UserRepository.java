package ru.otus.spring.springshellquiz.dao;

import ru.otus.spring.springshellquiz.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getUsers();

    Optional<User> getUserById(int id);

    User createUser(User user);

    void removeUser(int id);

    void save(User user);
}
