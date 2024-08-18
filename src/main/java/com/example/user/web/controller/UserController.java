package com.example.user.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
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

import com.example.user.domain.exceptions.user.AuthenticationException;
import com.example.user.domain.models.LoginDTO;
import com.example.user.domain.models.User;
import com.example.user.domain.models.UserCreateDTO;
import com.example.user.domain.models.UserGetResponseDTO;
import com.example.user.domain.services.UserService;
import com.example.user.infrastructure.mappers.UserMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
// Esse é o Controller (Camada intermediária entre a view e o service)
@RequiredArgsConstructor
public class UserController {
    // Importa o UserService e o UserMapper necessários para as operações do controlador.
    private final UserService userService; // Declara uma dependência para o serviço de usuário, que será injetada.
    private final UserMapper userMapper = UserMapper.INSTANCE; // Cria uma instância do UserMapper para mapear objetos User para DTOs.

    // Mapeia a requisição GET para obter um usuário pelo seu ID.
    @GetMapping("/{id}")
    public UserGetResponseDTO findById(@PathVariable int id) { // Obter o usuário pelo seu Id.
        User user = userService.findById(id); // Chama o serviço para buscar o usuário pelo ID.
        
        return userMapper.userToUserGetResponseDTO(user); // Converte o User para um UserGetResponseDTO e o retorna.
    }

    // Mapeia a requisição GET para obter uma lista de todos os usuários.
    @GetMapping
    public List<UserGetResponseDTO> findAll() {
        List<User> allUsers = userService.findAll(); // Chama o serviço para buscar todos os usuários.
        
        // Mapeia cada User para um UserGetResponseDTO e retorna a lista.
        return allUsers.stream().map(userMapper::userToUserGetResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/pages")
    public List<UserGetResponseDTO> listUsers(Pageable pageable) {
        Page<User> users = userService.listUsers(pageable);
        
        return users.map(userMapper::userToUserGetResponseDTO).getContent();
    }

    // Mapeia a requisição POST para o endpoint de login.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO data) {
        try {
            userService.login(data); // Chama o serviço para tentar realizar o login.
            return ResponseEntity.ok("Login efetuado com sucesso"); // Retorna uma resposta de sucesso se o login for bem-sucedido.
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Retorna uma resposta de erro de autenticação se as credenciais forem inválidas.
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Retorna uma resposta de erro genérico para outros problemas durante o login.
        }
    }

    // Mapeia a requisição POST para salvar um novo usuário.
    @PostMapping
    public UserGetResponseDTO save(@RequestBody @Valid UserCreateDTO data) {
        User newUser = userService.save(data); // Chama o serviço para salvar um novo usuário.

        return userMapper.userToUserGetResponseDTO(newUser); // Converte o User salvo para um UserGetResponseDTO e o retorna.
    }

    // Mapeia a requisição DELETE para deletar um usuário pelo seu ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        boolean deleted = userService.delete(id); // Chama o serviço para deletar o usuário pelo ID.

        if (deleted) {
            // Retorna uma resposta de sucesso se o usuário foi deletado.
            return ResponseEntity.ok("Usuário de Id: " + id + " foi deletado com sucesso!");
        } else {
            // Retorna uma resposta de "not found" se o usuário não foi encontrado.
            return ResponseEntity.notFound().build();
        }
    }
}