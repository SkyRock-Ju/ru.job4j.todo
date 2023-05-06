package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskStore taskStore;

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public List<Task> findDoneTasks() {
        return taskStore.findDoneTasks();
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public Task save(Task task) {
        return taskStore.save(task);
    }

    public boolean update(Task task) {
        return taskStore.update(task);
    }

    public boolean completeTask(int id) {
        return taskStore.completeTask(id);
    }

    public boolean delete(int id) {
        return taskStore.delete(id);
    }
}
