package com.skillforge.auth_service.DTO;

import com.skillforge.auth_service.models.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private UserRole role;
}
