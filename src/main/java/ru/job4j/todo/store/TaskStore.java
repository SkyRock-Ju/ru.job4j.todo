package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements TaskRepository {
    private final SessionFactory sessionFactory;


    @Override
    public Optional<Task> findById(int id) {
        var session = sessionFactory.openSession();
        try {
            var task = session.createQuery("FROM Task task WHERE task.id = :id", Task.class)
                    .setParameter("id", id).uniqueResultOptional();
            session.getTransaction().commit();
            return task;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return Optional.empty();
    }

    @Override
    public List<Task> findAll() {
        var session = sessionFactory.openSession();
        try {
            var tasks = session.createQuery("FROM Task", Task.class).list();
            session.getTransaction().commit();
            return tasks;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Task> findDoneTasks() {
        var session = sessionFactory.openSession();
        try {
            var doneTasks = session.createQuery("FROM Task WHERE done=true", Task.class).list();
            session.getTransaction().commit();
            return doneTasks;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return Collections.emptyList();
    }

    @Override
    public Task save(Task task) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
            return task;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET description = :description, done= :isDone WHERE id = :id",
                            Task.class)
                    .setParameter("description", task.getDescription())
                    .setParameter("isDone", task.isDone())
                    .setParameter("id", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return false;
    }

    @Override
    public boolean completeTask(int id) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET done=true WHERE id = :id", Task.class)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        var session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE FROM Task WHERE id = :id", Task.class)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sessionFactory.close();
        }
        return false;
    }
}
