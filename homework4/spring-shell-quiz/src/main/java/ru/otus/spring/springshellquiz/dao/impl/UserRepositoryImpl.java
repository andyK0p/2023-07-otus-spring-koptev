package ru.otus.spring.springshellquiz.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.springshellquiz.dao.UserRepository;
import ru.otus.spring.springshellquiz.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users;

    public UserRepositoryImpl(List<User> users) {
        this.users = users;
    }

    public UserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users
                .stream()
                .filter(student -> student.getId() == id)
                .findFirst();
    }

    @Override
    public User createUser(User user) {
        if (user != null) {
            user.setId(users.size() + 1);
            users.add(user);
        }
        return user;
    }

    @Override
    public void removeUser(int id) {
        users.remove(id - 1);
    }

    @Override
    public void save(User user) {
        if (user != null && user.getId() > 0) {
            removeUser(user.getId());
            int index = user.getId() - 1;
            users.add(index, user);
        }
    }
}
