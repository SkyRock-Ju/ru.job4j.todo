package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore implements UserRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserStore.class);

    @Override
    public Optional<User> save(User user) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            var user = session.createQuery("FROM User WHERE login = :login AND password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password).uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            var users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteById(int id) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE FROM User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }
}
