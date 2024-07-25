package com.example.user.exceptions.user;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationExceptionDetails {
    private String title;
    private int status;
    private String detail;
    private String developerMessage;
    private LocalDateTime timestamp;
    private List<String> fields;
    private List<String> fieldMessages;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private int status;
        private String detail;
        private String developerMessage;
        private LocalDateTime timestamp;
        private List<String> fields;
        private List<String> fieldMessages;

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
