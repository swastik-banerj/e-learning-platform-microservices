package com.skillforge.authservice.controllers;

import com.skillforge.authservice.DTO.LoginRequest;
import com.skillforge.authservice.DTO.LoginResponse;
import com.skillforge.authservice.DTO.RegisterRequest;
import com.skillforge.authservice.models.User;
import com.skillforge.authservice.security.JwtUtils;
import com.skillforge.authservice.services.UserService;
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
    public ResponseEntity<?> validate(HttpServletRequest request){
        return ResponseEntity.ok(userService.validateToken(request));
    }
}
