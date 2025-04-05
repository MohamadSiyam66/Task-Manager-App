package com.taskmanagerapp.Backend.services.auth;

import com.taskmanagerapp.Backend.dto.UserDto;
import com.taskmanagerapp.Backend.dto.UserRegisterReq;
import com.taskmanagerapp.Backend.entities.User;
import com.taskmanagerapp.Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    @Override
    public UserDto registerUser(UserRegisterReq userRegisterReq) {
        User user = new User();
        user.setUsername(userRegisterReq.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegisterReq.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public Boolean hasUserWithUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
