package com.example.user.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.mappers.UserMapper;
import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserCreateDTO;
import com.example.user.models.UserGetResponseDTO;
import com.example.user.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @GetMapping("/{id}")
    public UserGetResponseDTO getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);

        return userMapper.userToUserGetResponseDTO(user);
    }

    @GetMapping
    public List<UserGetResponseDTO> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
    
        return allUsers.stream().map(userMapper::userToUserGetResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/pages")
    public List<User> listUsers(Pageable pageable) {
        return userService.listUsers(pageable).getContent();
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
    public UserGetResponseDTO createUser(@RequestBody @Valid UserCreateDTO data) {
        User newUser = userService.createUser(data);

        return userMapper.userToUserGetResponseDTO(newUser);
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