package com.example.user.domain.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.user.domain.exceptions.user.AuthenticationException;
import com.example.user.domain.exceptions.user.NoUsersToListException;
import com.example.user.domain.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.domain.exceptions.user.UserIdNotFoundException;
import com.example.user.domain.exceptions.user.ValidationExceptionDetails;
import com.example.user.infrastructure.exception.GlobalExceptionHandler;

// Anotação @ControllerAdvice indica que esta classe fornecerá tratamento global de exceções para todos os controladores.
@ControllerAdvice
public class ExceptionHandler extends GlobalExceptionHandler {
    // Métodos de exceção específicos para User

    // Método para tratar exceções do tipo AuthenticationException.
    // Mapeado usando @ExceptionHandler para capturar exceções de autenticação.
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<String> authenticationHandler(AuthenticationException exception) {
        // Retorna uma resposta HTTP 404 (Not Found) com a mensagem da exceção.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // Método para tratar exceções do tipo UserEmailAlreadyExistsException.
    @org.springframework.web.bind.annotation.ExceptionHandler(UserEmailAlreadyExistsException.class)
    private ResponseEntity<String> userEmailAlreadyExistsHandler(UserEmailAlreadyExistsException exception) {
        // Retorna uma resposta HTTP 409 (Conflict) com a mensagem da exceção.
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    // Método para tratar exceções do tipo NoUsersToListException.
    @org.springframework.web.bind.annotation.ExceptionHandler(NoUsersToListException.class)
    private ResponseEntity<String> noUsersToListHandler(NoUsersToListException exception) {
        // Retorna uma resposta HTTP 204 (No Content) com a mensagem da exceção.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
    }

    // Método para tratar exceções do tipo UserIdNotFoundException.
    @org.springframework.web.bind.annotation.ExceptionHandler(UserIdNotFoundException.class)
    private ResponseEntity<String> userIdNotFoundHandler(UserIdNotFoundException exception) {
        // Retorna uma resposta HTTP 404 (Not Found) com a mensagem da exceção.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // Método para tratar exceções do tipo MethodArgumentNotValidException.
    // Este tipo de exceção ocorre quando a validação de argumentos de método falha.
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        // Extrai os erros de campo da exceção.
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        
        // Mapeia os nomes dos campos com erro para uma lista.
        List<String> fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.toList());
        
        // Mapeia as mensagens de erro correspondentes aos campos para outra lista.
        List<String> fieldMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        // Usa o Builder da classe ValidationExceptionDetails para construir um objeto detalhado da exceção.
        ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
                .timestamp(LocalDateTime.now()) // Define o timestamp atual.
                .status(HttpStatus.BAD_REQUEST.value()) // Define o status HTTP 400 (Bad Request).
                .title("Bad Request Exception, campos invalidos") // Define o título da exceção.
                .detail("Erro ao validar campos enviados") // Define uma mensagem detalhada sobre a exceção.
                .developerMessage(methodArgumentNotValidException.getClass().getName()) // Mensagem para desenvolvedores com o nome da classe de exceção.
                .fields(fields) // Adiciona a lista de campos com erro.
                .fieldMessages(fieldMessages) // Adiciona a lista de mensagens de erro.
                .build(); // Constrói o objeto ValidationExceptionDetails.

        // Retorna uma resposta HTTP 400 com o corpo contendo os detalhes da exceção.
        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @Override // Sobrescrevendo método da superclasse
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @Override // Sobrescrevendo método da superclasse
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}