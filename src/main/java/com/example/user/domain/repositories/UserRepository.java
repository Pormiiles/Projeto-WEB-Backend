package com.example.user.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.domain.models.User;

@Repository
// Este é o repositório da entidade User
public interface UserRepository extends JpaRepository<User, Integer> {
    // Métodos customizados para busca e verificação de dados baseado em um campo da entidade
    Optional<User> findByEmail(String email); // Retorna o usuário pelo seu email
    boolean existsByEmail(String email); // Verifica se o usuário existe pelo seu email
}
