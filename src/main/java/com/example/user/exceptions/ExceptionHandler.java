package com.example.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.exceptions.user.NoUsersToListException;
import com.example.user.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.exceptions.user.UserIdNotFoundException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<String> authenticationHandler(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserEmailAlreadyExistsException.class)
    private ResponseEntity<String> userEmailAlreadyExistsHandler(UserEmailAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoUsersToListException.class)
    private ResponseEntity<String> noUsersToListHandler(NoUsersToListException exception) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserIdNotFoundException.class)
    private ResponseEntity<String> userIdNotFoundHandler(UserIdNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}

