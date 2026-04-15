package com.skillforge.auth_service.controllers;

import com.skillforge.auth_service.DTO.LoginRequest;
import com.skillforge.auth_service.DTO.LoginResponse;
import com.skillforge.auth_service.DTO.RegisterRequest;
import com.skillforge.auth_service.DTO.UserResponse;
import com.skillforge.auth_service.models.User;
import com.skillforge.auth_service.security.JwtUtils;
import com.skillforge.auth_service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/public")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{

            User user = userService.authenticate(loginRequest);

            String token = jwtUtils.generateToken(String.valueOf(user.getId()), user.getRole().name());

            return ResponseEntity.ok(new LoginResponse(token, userService.convertToDto(user)));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<UserResponse> validateUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.validateUser(token));
    }
}