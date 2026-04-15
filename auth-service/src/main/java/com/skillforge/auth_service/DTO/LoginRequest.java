package com.skillforge.auth_service.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
