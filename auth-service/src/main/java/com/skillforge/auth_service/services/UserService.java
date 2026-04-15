package com.skillforge.auth_service.services;

import com.skillforge.auth_service.DTO.LoginRequest;
import com.skillforge.auth_service.DTO.RegisterRequest;
import com.skillforge.auth_service.DTO.UserResponse;
import com.skillforge.auth_service.models.User;
import com.skillforge.auth_service.models.UserRole;
import com.skillforge.auth_service.repositories.UserRepository;
import com.skillforge.auth_service.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /* public UserResponse validateToken(HttpServletRequest request) {
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

    }*/

    public UserResponse convertToDto(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public Boolean validateUser(String userId) {
        return userRepository.existsById(Long.valueOf(userId));
    }
}
