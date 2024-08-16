package com.example.user.exceptions.user;

// Exception para caso o usuário não esteja cadastrado no sistema
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
