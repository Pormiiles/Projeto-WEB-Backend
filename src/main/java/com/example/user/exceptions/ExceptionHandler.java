package com.example.user.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.exceptions.user.NoUsersToListException;
import com.example.user.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.exceptions.user.UserIdNotFoundException;
import com.example.user.exceptions.user.ValidationExceptionDetails;

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

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        
        List<String> fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.toList());
        
        List<String> fieldMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, campos invalidos")
                .detail("Erro ao validar campos enviados")
                .developerMessage(methodArgumentNotValidException.getClass().getName())
                .fields(fields)
                .fieldMessages(fieldMessages)
                .build();

        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }
}

