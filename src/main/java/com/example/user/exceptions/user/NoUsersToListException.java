package com.example.user.exceptions.user;

public class NoUsersToListException extends RuntimeException {
    public NoUsersToListException(String message) {
        super(message);
    }
}
