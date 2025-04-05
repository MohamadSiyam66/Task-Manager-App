package com.taskmanagerapp.Backend.repositories;

import com.taskmanagerapp.Backend.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

