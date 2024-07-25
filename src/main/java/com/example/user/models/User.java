package com.example.user.models;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    private int idade;

    private String email;

    private String password;

    public User(UserCreateDTO data) {
        this.nome = data.nome();
        this.idade = data.idade();
        this.email = data.email();
        setPassword(data.password());
    }

    public static User fromDTOWithEncryptedPassword(UserCreateDTO data) {
        User user = new User(data);
        user.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));
        
        return user;
    }
}
