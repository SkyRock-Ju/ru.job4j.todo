package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CategoryStore implements CategoryRepository {

    private CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("From Category", Category.class);
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return crudRepository.query(
                "FROM Category category WHERE category.id IN :ids", Category.class,
                Map.of("ids", ids)
        );
    }
}
