package com.taskmanagerapp.Backend.controllers;

import com.taskmanagerapp.Backend.dto.TaskDto;
import com.taskmanagerapp.Backend.entities.Task;
import com.taskmanagerapp.Backend.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/task")
    public  ResponseEntity<Task> createTask(@RequestBody Task task){
        try {
            Task createdTask = userService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(userService.getAllTasks());

    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        userService.deleteTask(id);
        return ResponseEntity.ok(null);

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getTaskById(id));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto){
       TaskDto updateTask =userService.updateTask(id, taskDto);
       if(updateTask != null){
           return ResponseEntity.ok(updateTask);
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }

    }

}
