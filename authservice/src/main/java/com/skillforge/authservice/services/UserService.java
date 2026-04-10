package com.skillforge.authservice.services;

import com.skillforge.authservice.DTO.RegisterRequest;
import com.skillforge.authservice.DTO.UserResponse;
import com.skillforge.authservice.models.User;
import com.skillforge.authservice.repositories.UserRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            User existingUser = userRepository.findByEmail(registerRequest.getEmail());
            return convertToDto(existingUser);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole(registerRequest.getRole());
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    private UserResponse convertToDto(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
