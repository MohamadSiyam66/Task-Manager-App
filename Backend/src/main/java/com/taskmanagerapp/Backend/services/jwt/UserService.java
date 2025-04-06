package com.taskmanagerapp.Backend.services.jwt;

import com.taskmanagerapp.Backend.dto.TaskDto;
import com.taskmanagerapp.Backend.dto.UserDto;
import com.taskmanagerapp.Backend.entities.Task;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailService();
    List<UserDto> getUsers();
    Task createTask(Task task);
    List<TaskDto> getAllTasks();
    void deleteTask(Long id);
    TaskDto getTaskById(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
}
