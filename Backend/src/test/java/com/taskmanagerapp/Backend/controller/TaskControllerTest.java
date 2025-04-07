package com.taskmanagerapp.Backend.controller;

import com.taskmanagerapp.Backend.controllers.TaskController;
import com.taskmanagerapp.Backend.entities.Task;
import com.taskmanagerapp.Backend.services.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("This is task 1");
        task1.setStatus("OPEN");
        task1.setCreatedAt(LocalDateTime.now());

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("This is task 2");
        task2.setStatus("IN_PROGRESS");
        task2.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testGetAllTasks() throws Exception {
        // Arrange
        given(taskService.getAllTasks()).willReturn(Arrays.asList(task1, task2));

        // Act and Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task 1"))
                .andExpect(jsonPath("$[1].title").value("Test Task 2"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        // Arrange
        given(taskService.getTaskById(1L)).willReturn(Optional.of(task1));

        // Act and Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task 1"))
                .andExpect(jsonPath("$.description").value("This is task 1"));
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        // Arrange
        given(taskService.getTaskById(999L)).willReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask() throws Exception {
        // Arrange
        given(taskService.createTask(task1)).willReturn(task1);

        // Act and Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Test Task 1\",\"description\":\"This is task 1\",\"status\":\"OPEN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task 1"))
                .andExpect(jsonPath("$.description").value("This is task 1"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        // Arrange
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Task 1");
        updatedTask.setDescription("Updated description");
        updatedTask.setStatus("COMPLETED");
        updatedTask.setCreatedAt(task1.getCreatedAt());

        given(taskService.updateTask(1L, task1)).willReturn(updatedTask);

        // Act and Assert
        mockMvc.perform(put("/api/tasks/1")
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Task 1\",\"description\":\"Updated description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task 1"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        // Arrange
        taskService.deleteTask(1L);

        // Act and Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        // Verify that the service method was called
        verify(taskService, times(1)).deleteTask(1L);
    }
}
