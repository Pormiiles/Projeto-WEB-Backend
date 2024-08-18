package com.example.user.domain.exceptions.user;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// Anotações @Getter e @Setter geram automaticamente os métodos getter e setter para todos os campos da classe.
@Getter
@Setter
public class ValidationExceptionDetails {
    private String title; // Título da exceção ou erro.
    private int status; // Código de status HTTP associado à exceção.
    private String detail; // Detalhes da exceção ou erro.
    private String developerMessage; // Mensagem destinada aos desenvolvedores, útil para depuração.
    private LocalDateTime timestamp; // Timestamp indicando o momento em que a exceção ocorreu.
    private List<String> fields; // Lista de nomes dos campos que causaram a exceção (em caso de validação de formulário, por exemplo).
    private List<String> fieldMessages; // Lista de mensagens de erro associadas aos campos específicos.

    // Método estático que cria uma instância do Builder, usado para construir objetos ValidationExceptionDetails.
    public static Builder builder() {
        return new Builder();
    }

    // Classe estática interna Builder, que implementa o padrão de projeto Builder para facilitar a criação de objetos ValidationExceptionDetails.
    public static class Builder {
        private String title;
        private int status;
        private String detail;
        private String developerMessage;
        private LocalDateTime timestamp;
        private List<String> fields;
        private List<String> fieldMessages;

        // Métodos do Builder para definir cada campo da classe ValidationExceptionDetails.
        // Cada método retorna a própria instância do Builder para permitir encadeamento (chaining).
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder fields(List<String> fields) {
            this.fields = fields;
            return this;
        }

        public Builder fieldMessages(List<String> fieldMessages) {
            this.fieldMessages = fieldMessages;
            return this;
        }

        // Método final para construir o objeto ValidationExceptionDetails usando os valores definidos no Builder.
        public ValidationExceptionDetails build() {
            ValidationExceptionDetails details = new ValidationExceptionDetails();
            details.title = this.title;
            details.status = this.status;
            details.detail = this.detail;
            details.developerMessage = this.developerMessage;
            details.timestamp = this.timestamp;
            details.fields = this.fields;
            details.fieldMessages = this.fieldMessages;
            return details;
        }
    }
}