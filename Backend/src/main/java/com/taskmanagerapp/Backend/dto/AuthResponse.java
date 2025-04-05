package com.taskmanagerapp.Backend.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private Long userId;
    private String username;

}
