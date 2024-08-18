package com.example.user.domain.exceptions.user;

// Exception para caso não haja nenhum usuário para retornar
public class NoUsersToListException extends RuntimeException {
    public NoUsersToListException(String message) {
        super(message);
    }
}
