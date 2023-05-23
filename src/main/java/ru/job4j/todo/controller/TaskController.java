package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String getTasks(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute(
                "tasks",
                taskService.findAll().stream().peek(task -> {
                            LocalDateTime time = task.getCreated();
                            task.setCreated(
                                    ZonedDateTime.of(
                                            time,
                                            ZoneId.of("UTC")
                                    ).withZoneSameInstant(
                                            ZoneId.of(user.getTimezone())
                                    ).toLocalDateTime()
                            );

                        }
                ).collect(Collectors.toList())
        );
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
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session, @RequestParam("categories.ids") List<Integer> ids) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        taskService.save(task, ids);
        return "redirect:/tasks";
    }
}