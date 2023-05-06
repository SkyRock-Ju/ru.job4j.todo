package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(int id);

    List<User> findAll();

    User save(User user);

    boolean update(User user);

    boolean delete(int id);
}
