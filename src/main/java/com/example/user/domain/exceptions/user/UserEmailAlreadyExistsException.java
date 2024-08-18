package com.example.user.domain.exceptions.user;

// Exception para caso algum email já exista no banco de dados 
public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
