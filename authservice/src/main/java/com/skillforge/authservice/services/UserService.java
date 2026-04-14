package com.skillforge.authservice.services;

import com.skillforge.authservice.DTO.LoginRequest;
import com.skillforge.authservice.DTO.RegisterRequest;
import com.skillforge.authservice.DTO.UserResponse;
import com.skillforge.authservice.models.User;
import com.skillforge.authservice.models.UserRole;
import com.skillforge.authservice.repositories.UserRepository;
import com.skillforge.authservice.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

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

    public UserResponse validateToken(HttpServletRequest request) {
        String jwtToken = jwtUtils.getJwtFromHeader(request);
        if(!jwtUtils.validateJwtToken(jwtToken)){
            throw new RuntimeException("Invalid Token");
        }

        String userId = jwtUtils.getUserIdFromToken(jwtToken);

        if(userId == null){
           throw new RuntimeException("User Id is null"); 
        }

        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDto(user);

    }

    public UserResponse convertToDto(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
