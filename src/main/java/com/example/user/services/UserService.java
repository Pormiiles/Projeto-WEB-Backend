package com.example.user.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.user.exceptions.user.AuthenticationException;
import com.example.user.exceptions.user.UserIdNotFoundException;
import com.example.user.exceptions.user.NoUsersToListException;
import com.example.user.exceptions.user.UserEmailAlreadyExistsException;
import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserDTO;
import com.example.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException("Usuário de Id: " + id + " não foi encontrado!"));
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        if(allUsers.isEmpty())
            throw new NoUsersToListException("Não há usuários para listar!");

        return allUsers;
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

    public User createUser(UserDTO data) {
        Optional<User> userOptional = userRepository.findByEmail(data.email());
        if(userOptional.isPresent())
            throw new UserEmailAlreadyExistsException("Erro! Já existe um usuário com o mesmo email cadastrado.");

        User newUser = new User();
        newUser.setNome(data.nome());
        newUser.setIdade(data.idade());
        newUser.setEmail(data.email());
        newUser.setPassword(BCrypt.hashpw(data.password(), BCrypt.gensalt()));

        return userRepository.save(newUser);
    }

    public boolean deleteUser(int id) {
        User user = getUserById(id);

        userRepository.delete(user);

        return true;
    }
}
