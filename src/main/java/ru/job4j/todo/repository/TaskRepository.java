package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(int id);

    List<Task> findAll();

    List<Task> findDoneTasks();

    List<Task> findNewTasks();

    Task save(Task task);

    void update(Task task);

    void completeTask(int id);

    void delete(int id);
}
