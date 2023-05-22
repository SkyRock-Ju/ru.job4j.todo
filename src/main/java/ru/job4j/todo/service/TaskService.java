package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryStore;
import ru.job4j.todo.repository.PriorityStore;
import ru.job4j.todo.repository.TaskStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskStore taskStore;
    private PriorityStore priorityStore;
    private CategoryStore categoryStore;

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public List<Task> findDoneTasks() {
        return taskStore.findDoneTasks();
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public List<Task> findNewTasks() {
        return taskStore.findNewTasks();
    }

    public void save(Task task, List<Integer> ids) {
        task.setCreated(LocalDateTime.now());
        task.setPriority(priorityStore.findById(task.getPriority().getId()).orElseThrow());
        task.setCategories(categoryStore.findByIds(ids));
        taskStore.update(task);
    }

    public void update(Task task) {
        taskStore.update(task);
    }

    public void completeTask(int id) {
        taskStore.completeTask(id);
    }

    public void delete(int id) {
        taskStore.delete(id);
    }
}
