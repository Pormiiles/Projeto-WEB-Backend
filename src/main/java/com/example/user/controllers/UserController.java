package com.example.user.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.exceptions.user.UserIdNotFoundException;
import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserDTO;
import com.example.user.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
 
            return ResponseEntity.ok(user);
        } catch(UserIdNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } 
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
    
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO data) {
        try {
            userService.login(data);
            return ResponseEntity.ok("Login efetuado com sucesso");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data) {
        User newUser = userService.createUser(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);

        if(deleted) {
            return ResponseEntity.ok("Usuário de Id: " + id + " foi deletado com sucesso!");
        } else
            return ResponseEntity.notFound().build();
    }
}