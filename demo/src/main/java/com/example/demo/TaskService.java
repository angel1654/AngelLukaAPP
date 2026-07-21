package com.example.demo;

import com.example.demo.CustomExceptions.BlankTaskDescriptionException;
import com.example.demo.CustomExceptions.BlankTaskTitleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, JwtUtil jwtUtil, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    public List<Task> getTasks()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        return user.getTasks();
    }

    public ResponseEntity<Task> createTask(String taskTitle, String taskDescription)
    {
        if(taskTitle.isBlank())
        {
            throw new BlankTaskTitleException("You must enter a name for the task!");
        }
        else if(taskDescription.isBlank())
        {
            throw new BlankTaskDescriptionException("You must enter a task description!");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Task task = new Task(taskTitle,taskDescription,"in_progress", user);
        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.CREATED);
    }
}
