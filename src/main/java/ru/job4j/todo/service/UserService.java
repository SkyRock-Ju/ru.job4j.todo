package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserStore userStore;

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User save(User user) {
        return userStore.save(user);
    }

    public boolean update(User user) {
        return userStore.update(user);
    }

    public boolean delete(int id) {
        return userStore.delete(id);
    }
}
