package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task WHERE id = :id", Task.class,
                Map.of("id", id)
        );
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query("FROM Task", Task.class);
    }

    @Override
    public List<Task> findDoneTasks() {
        return crudRepository.query("FROM Task WHERE done=true", Task.class);
    }

    @Override
    public List<Task> findNewTasks() {
        return crudRepository.query("FROM Task WHERE created> :date", Task.class,
                    Map.of("date", LocalDateTime.now().minusDays(1))
        );
    }

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.merge(task));
    }

    @Override
    public void completeTask(int id) {
        crudRepository.query("UPDATE Task SET done=true WHERE id = :id", Task.class,
                    Map.of("id", id)
        );
    }

    @Override
    public void delete(int id) {
        crudRepository.query("DELETE FROM Task WHERE id = :id", Task.class,
                    Map.of("id", id)
        );
    }
}
