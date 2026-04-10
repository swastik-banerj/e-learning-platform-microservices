package com.skillforge.authservice.services;

import com.skillforge.authservice.DTO.LoginRequest;
import com.skillforge.authservice.DTO.RegisterRequest;
import com.skillforge.authservice.DTO.UserResponse;
import com.skillforge.authservice.models.User;
import com.skillforge.authservice.models.UserRole;
import com.skillforge.authservice.repositories.UserRepository;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            User existingUser = userRepository.findByEmail(registerRequest.getEmail());
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.ROLE_STUDENT);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null){
            throw new RuntimeException("Invalid Credentials");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        return user;
    }

    public UserResponse convertToDto(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
