package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        return "task/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model) {
        List<Task> doneTasks = taskService.findNewTasks();
        model.addAttribute("tasks", doneTasks);
        return "task/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу");
            return "404";
        }
        model.addAttribute("task", task.get());
        return "task/one";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(Model model, @PathVariable int id) {
        taskService.completeTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("update/{id}")
    public String updateTask(Model model, @PathVariable int id) {
        var task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу");
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        taskService.save(task);
        return "redirect:/tasks";
    }
}