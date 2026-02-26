package com.sujal.auth_service.service;

import com.sujal.auth_service.config.SecurityConfig;
import com.sujal.auth_service.model.JwtTokenResponse;
import com.sujal.auth_service.model.User;
import com.sujal.auth_service.model.UserDto;
import com.sujal.auth_service.repository.UserRepository;
import com.sujal.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDto saveUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        UserDto userDto = new UserDto();
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setUsername(savedUser.getUsername());
        return userDto;
    }

    public JwtTokenResponse generateToken(String username) {
        String token = jwtUtil.generateToken(username);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        jwtTokenResponse.setToken(token);
        jwtTokenResponse.setType("Bearer");
        jwtTokenResponse.setValidUntil(jwtUtil.extractExpiration(token).toString());
        return jwtTokenResponse;
    }
}