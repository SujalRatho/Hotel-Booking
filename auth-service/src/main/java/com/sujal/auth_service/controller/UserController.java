package com.sujal.auth_service.controller;

import com.sujal.auth_service.exception.BadRequestException;
import com.sujal.auth_service.model.JwtTokenResponse;
import com.sujal.auth_service.model.LoginRequest;
import com.sujal.auth_service.model.User;
import com.sujal.auth_service.model.UserDto;
import com.sujal.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserDto register(@RequestBody User user) {
        try {
            return userService.saveUser(user);
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            throw new BadRequestException("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/generate-token")
    public JwtTokenResponse generateToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return userService.generateToken(loginRequest.getUsername());
            } else {
                throw new BadRequestException("Authentication failed");
            }
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            throw new BadRequestException("Login failed: " + e.getMessage());
        }
    }
}
