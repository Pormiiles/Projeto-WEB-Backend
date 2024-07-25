package com.example.user.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank(message = "O campo 'nome' eh obrigatorio!")
    String nome,

    @Min(value = 0, message = "A idade nao pode ser negativa!")
    int idade,

    @Email(message = "O e-mail inserido eh invalido!")
    @NotBlank(message = "O campo 'email' eh obrigatorio!")
    String email,

    @NotBlank(message = "O campo 'senha' eh obrigatorio!")
    String password
) {}