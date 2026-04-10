package com.skillforge.authservice.DTO;

import com.skillforge.authservice.models.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
