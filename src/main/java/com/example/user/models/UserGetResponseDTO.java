package com.example.user.models;

// DTO para obter um simples retorno de dados sobre o usuário (atributos básicos: nome e idade)
public record UserGetResponseDTO(String nome, int idade) {
    
}
