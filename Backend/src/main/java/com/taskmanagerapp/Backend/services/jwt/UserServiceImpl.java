package com.taskmanagerapp.Backend.services.jwt;

import com.taskmanagerapp.Backend.dto.TaskDto;
import com.taskmanagerapp.Backend.dto.UserDto;
import com.taskmanagerapp.Backend.entities.Task;
import com.taskmanagerapp.Backend.entities.User;
import com.taskmanagerapp.Backend.repositories.TaskRepository;
import com.taskmanagerapp.Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }


    @Override
    public Task createTask(Task task) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            task.setUser(user);
            return taskRepository.save(task);
        } else {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
    }

    @Override
    @Transactional
    public List<TaskDto> getAllTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Get tasks of the user
            List<Task> tasks = taskRepository.findByUserId(user.getId());

            return tasks.stream()
                    .map(Task::getTaskDto)
                    .sorted(Comparator.comparing(TaskDto::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
        } else {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
    }

    @Override
    public void deleteTask(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Task> taskOpt = taskRepository.findById(id);

            if (taskOpt.isPresent() && taskOpt.get().getUser().getId().equals(user.getId())) {
                taskRepository.deleteById(id);
            } else {
                throw new RuntimeException("Task not found or you are not authorized to delete it");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setStatus(taskDto.getStatus());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByStatus(String status) {
        return taskRepository.findAllByStatus(status)
                .stream()
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }


}
