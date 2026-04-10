package com.skillforge.authservice.DTO;

import com.skillforge.authservice.models.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private UserRole role;
}
