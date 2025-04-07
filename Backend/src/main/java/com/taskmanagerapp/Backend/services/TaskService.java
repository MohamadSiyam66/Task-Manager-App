package com.taskmanagerapp.Backend.services;

import com.taskmanagerapp.Backend.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    // Method to get all tasks
    List<Task> getAllTasks();

    // Method to get a task by its ID
    Optional<Task> getTaskById(Long id);

    // Method to create a new task
    Task createTask(Task task);

    // Method to update an existing task
    Task updateTask(Long id, Task taskDetails);

    // Method to delete a task by its ID
    void deleteTask(Long id);
}
