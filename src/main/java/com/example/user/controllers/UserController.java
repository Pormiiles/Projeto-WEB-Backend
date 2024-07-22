package com.example.user.controllers;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserDTO;
import com.example.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    // Acesso ao métodos do repositório
    private final UserRepository userRepository;

    // Método para obter o usuário pelo seu Id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) 
            return ResponseEntity.ok(user);
        else 
            return ResponseEntity.notFound().build();
    }

    // Método para obter todos os usuários cadastrados
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
    
        return ResponseEntity.ok(allUsers);
    }

    // Método para logar usuário. Utiliza um DTO passando e-mail e senha do usuário para a operação
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO data) {
        Optional<User> userOptional = userRepository.findByEmail(data.email());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(BCrypt.checkpw(data.password(), user.getPassword()))
                return ResponseEntity.ok("Login efetuado com sucesso");
            else
                return ResponseEntity.status(401).body("Senha incorreta");
        } else
            return ResponseEntity.status(401).body("Usuário não encontrado");
    }

    // Método para criar usuário
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO data) {
        User newUser = new User();
        newUser.setNome(data.nome());
        newUser.setIdade(data.idade());
        newUser.setEmail(data.email());
        newUser.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));
        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    // Método para excluir usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }
}
