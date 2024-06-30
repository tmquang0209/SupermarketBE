package com.supermarket.backend.Payload.Request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    @Override
    public String toString(){

        return "Username: " + username + ", Password: " + password;
    }
}
