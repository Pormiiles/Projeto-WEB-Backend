package com.example.user.domain.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user.domain.exceptions.user.AuthenticationException;
import com.example.user.domain.exceptions.user.NoUsersToListException;
import com.example.user.domain.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.domain.exceptions.user.UserIdNotFoundException;
import com.example.user.domain.models.LoginDTO;
import com.example.user.domain.models.User;
import com.example.user.domain.models.UserCreateDTO;
import com.example.user.domain.repositories.UserPagesRepository;
import com.example.user.domain.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// Essa é o Service da aplicação (contém as regras de negócio e principais validações da aplicação)
public class UserService {
    private final UserRepository userRepository;
    private final UserPagesRepository userPagesRepository;

    public User findById(int id) { // Obter o usuário pelo Id dele
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("Usuário de Id: " + id + " não foi encontrado!")); // Busca no repositório, se não encontrar, retorna uma exception customizada
    }

    public List<User> findAll() { // Obter todos os usuários 
        List<User> allUsers = userRepository.findAll(); // Busca no repositório todos os usuários existentes/cadastrados

        if(allUsers.isEmpty())
            throw new NoUsersToListException("Não há usuários para listar!"); // Se não houver nenhum usuário, retorna uma exception

        return allUsers;
    }

    public Page<User> listUsers(Pageable pageable) { // Método de paginação de dados
        return userPagesRepository.findAll(pageable); 
    }

    public Optional<User> login(LoginDTO data) throws AuthenticationException { // Fazer login de usuário
        Optional<User> userOptional = userRepository.findByEmail(data.email()); // Verifica se o usuário existe pelo e-mail

        if(userOptional.isPresent()) { // Validação do optional
            User user = userOptional.get(); // Atribui o optional a uma instância de usuário
            if(!BCrypt.checkpw(data.password(), user.getPassword())) // Valida a senha
                throw new AuthenticationException("Senha incorreta");
        } else
            throw new AuthenticationException("Usuário não encontrado"); // Retorna exception caso o usuário não exista

        return userOptional;
    }

    @Transactional
    public User save(@Valid UserCreateDTO data) { // Criação/Registro de usuário
        Optional<User> userOptional = userRepository.findByEmail(data.email()); // Verifica se o usuário existe pelo e-mail
        if(userOptional.isPresent())
            throw new UserEmailAlreadyExistsException("Erro! Já existe um usuário com o mesmo email cadastrado"); // Se já existir um usuário cadastrado com o mesmo e-mail, retorna um exception

        User newUser = User.fromDTOWithEncryptedPassword(data); // Cadastra todos os dados do usuário + senha criptografa
        
        return userRepository.save(newUser);
    }

    public boolean delete(int id) {
        User user = findById(id); // Verifica se o usuário existe pelo seu Id

        userRepository.delete(user); // Deleta da base de dados

        return true;
    }
}