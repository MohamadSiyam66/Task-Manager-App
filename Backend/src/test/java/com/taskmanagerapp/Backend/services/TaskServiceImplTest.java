package com.taskmanagerapp.Backend.services;

import com.taskmanagerapp.Backend.entities.Task;
import com.taskmanagerapp.Backend.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;  // Mock the repository

    @InjectMocks
    private TaskServiceImpl taskService;    // Inject mocks into the service

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
    }

    @Test
    void testCreateTask() {
        // Mock the repository's save method
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Call the service method
        Task createdTask = taskService.createTask(task);

        // Assert that the task is created correctly
        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("Test Description", createdTask.getDescription());

        // Verify that the save method was called once
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task();
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setTitle("Task 2");

        // Mock the repository's findAll method
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        // Call the service method
        List<Task> tasks = taskService.getAllTasks();

        // Assert that the correct tasks are returned
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void testGetTaskById() {
        // Mock the repository's findById method
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Call the service method
        Optional<Task> fetchedTask = taskService.getTaskById(1L);

        // Assert that the task is returned correctly
        assertTrue(fetchedTask.isPresent());
        assertEquals("Test Task", fetchedTask.get().getTitle());
    }

    @Test
    void testUpdateTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");

        // Mock the repository's findById and save methods
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Call the service method
        Task result = taskService.updateTask(1L, updatedTask);

        // Assert that the task was updated correctly
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());

        // Verify that the save method was called once
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void testDeleteTask() {
        long taskId = 1L;

        // Mock the repository's deleteById method
        doNothing().when(taskRepository).deleteById(taskId);

        // Call the service method
        taskService.deleteTask(taskId);

        // Verify that the delete method was called once
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testUpdateTask_NotFound() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated Description");

        // Mock the repository's findById method to return an empty Optional
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and assert that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.updateTask(1L, updatedTask));

        // Assert the exception message
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testGetTaskById_NotFound() {
        // Mock the repository's findById method to return an empty Optional
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method
        Optional<Task> fetchedTask = taskService.getTaskById(1L);

        // Assert that the task is not found
        assertFalse(fetchedTask.isPresent());
    }
}
