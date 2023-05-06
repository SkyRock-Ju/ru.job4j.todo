package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskStore;
import ru.job4j.todo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore implements UserRepository {

    private final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskStore.class);

    @Override
    public Optional<User> findById(int id) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            var user = session.createQuery("FROM User WHERE id = :id", User.class)
                    .setParameter("id", id).uniqueResultOptional();
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
    public List<User> findAll() {
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
    public User save(User user) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE User SET name = :name, login = :login, password = :password WHERE id = :id")
                    .setParameter("name", user.getName())
                    .setParameter("login", user.getLogin())
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
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

    @Override
    public boolean delete(int id) {
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
