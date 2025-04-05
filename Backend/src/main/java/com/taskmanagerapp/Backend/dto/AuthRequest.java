package com.taskmanagerapp.Backend.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
