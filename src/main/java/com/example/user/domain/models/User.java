package com.example.user.domain.models;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// Esta é a entidade modelo de User
public class User {
    // Atributos da classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    private int idade;

    private String email;

    private String password;

    // Construtor para inicializar os dados vindos do DTO (create user)
    public User(UserCreateDTO data) {
        this.nome = data.nome();
        this.idade = data.idade();
        this.email = data.email();
        setPassword(data.password());
    }

    // Cria um novo usuário com todos os seu dados e senha criptografada
    public static User fromDTOWithEncryptedPassword(UserCreateDTO data) {
        User user = new User(data);
        user.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));
        
        return user;
    }
}
