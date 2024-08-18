package com.example.user.domain.models;

// DTO para passar dados para o método de login (email, password)
public record LoginDTO(String email, String password) {
    
}
