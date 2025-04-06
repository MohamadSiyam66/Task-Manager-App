package com.taskmanagerapp.Backend.dto;

import lombok.Data;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;

    private Long userId;
    private String userName;
}

