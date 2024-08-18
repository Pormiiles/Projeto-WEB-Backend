package com.example.user.domain.exceptions.user;

// Exception para caso nenhum usuário for encontrado pelo seu Id
public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
