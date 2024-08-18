package com.example.user.domain.exceptions.user;

// Exception para caso o usuário não esteja cadastrado no sistema
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
