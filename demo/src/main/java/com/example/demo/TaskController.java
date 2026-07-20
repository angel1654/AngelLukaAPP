package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/{username}/tasks")
    public List<Task> getTask()
    {
        return taskService.getTasks();
    }
    @PostMapping("/api/{username}/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.createTask(task.getTaskTitle(),task.getTaskDescription());
    }
}
