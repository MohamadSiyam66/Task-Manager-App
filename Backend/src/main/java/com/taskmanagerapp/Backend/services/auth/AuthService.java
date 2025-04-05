package com.taskmanagerapp.Backend.services.auth;

import com.taskmanagerapp.Backend.dto.UserDto;
import com.taskmanagerapp.Backend.dto.UserRegisterReq;

public interface AuthService {
    UserDto registerUser(UserRegisterReq userRegisterReq);
    Boolean hasUserWithUsername(String username);
}
