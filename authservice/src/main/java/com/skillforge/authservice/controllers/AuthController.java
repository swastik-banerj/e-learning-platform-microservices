package com.skillforge.authservice.controllers;

import com.skillforge.authservice.DTO.RegisterRequest;
import com.skillforge.authservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/public")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }
}
