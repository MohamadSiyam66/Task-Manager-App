package com.taskmanagerapp.Backend.controllers;

import com.taskmanagerapp.Backend.dto.AuthRequest;
import com.taskmanagerapp.Backend.dto.AuthResponse;
import com.taskmanagerapp.Backend.dto.UserDto;
import com.taskmanagerapp.Backend.dto.UserRegisterReq;
import com.taskmanagerapp.Backend.entities.User;
import com.taskmanagerapp.Backend.repositories.UserRepository;
import com.taskmanagerapp.Backend.security.JwtUtil;
import com.taskmanagerapp.Backend.services.auth.AuthService;
import com.taskmanagerapp.Backend.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterReq userRegisterReq) {
        if(authService.hasUserWithUsername(userRegisterReq.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Username already exists");
        }
        UserDto createdUserDto = authService.registerUser(userRegisterReq);
        if(createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            final UserDetails userDetails = userService.userDetailService()
                    .loadUserByUsername(authRequest.getUsername());

            Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());

            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Invalid username or password");
            }

            final String jwtToken = jwtUtil.generateToken(userDetails);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwtToken);
            authResponse.setUserId(optionalUser.get().getId());
            authResponse.setUsername(optionalUser.get().getUsername());

            return ResponseEntity.ok(authResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }
}