package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserStore userStore;

    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }

    public Collection<User> findAll() {
        return userStore.findAll();
    }

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }
}
