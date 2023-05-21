package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore implements UserRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User WHERE login = :login AND password = :password",
                User.class,
                Map.of("login", login, "password", password)
        );
    }

    @Override
    public Collection<User> findAll() {
        return crudRepository.query("FROM User", User.class);
    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "FROM User WHERE id = :id",
                User.class,
                Map.of("id", id)
        );
    }

    @Override
    public void deleteById(int id) {
        crudRepository.query(
                "DELETE FROM User WHERE id = :id",
                User.class,
                Map.of("id", id)
        );
    }
}
