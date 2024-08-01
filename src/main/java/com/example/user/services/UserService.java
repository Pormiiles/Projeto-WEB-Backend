package com.example.user.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.exceptions.user.NoUsersToListException;
import com.example.user.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.exceptions.user.UserIdNotFoundException;
import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserCreateDTO;
import com.example.user.repositories.UserPagesRepository;
import com.example.user.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserPagesRepository userPagesRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("Usuário de Id: " + id + " não foi encontrado!"));
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        if(allUsers.isEmpty())
            throw new NoUsersToListException("Não há usuários para listar!");

        return allUsers;
    }

    public Page<User> listUsers(Pageable pageable) {
        return userPagesRepository.findAll(pageable);
    }

    public Optional<User> login(LoginDTO data) throws AuthenticationException {
        Optional<User> userOptional = userRepository.findByEmail(data.email());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(!BCrypt.checkpw(data.password(), user.getPassword())) 
                throw new AuthenticationException("Senha incorreta");
        } else
            throw new AuthenticationException("Usuário não encontrado");

        return userOptional;
    }

    @Transactional
    public User createUser(@Valid UserCreateDTO data) {
        Optional<User> userOptional = userRepository.findByEmail(data.email());
        if(userOptional.isPresent())
            throw new UserEmailAlreadyExistsException("Erro! Já existe um usuário com o mesmo email cadastrado");

        User newUser = User.fromDTOWithEncryptedPassword(data);
        
        return userRepository.save(newUser);
    }

    public boolean deleteUser(int id) {
        User user = getUserById(id);

        userRepository.delete(user);

        return true;
    }
}