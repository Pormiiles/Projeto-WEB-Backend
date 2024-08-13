package com.example.user.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.user.models.LoginDTO;
import com.example.user.models.User;
import com.example.user.models.UserCreateDTO;

import reactor.core.publisher.Mono;

@Service
public class SpringClient {

    private final WebClient webClient;

    public SpringClient(@Value("${api.base.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<User> createUser(UserCreateDTO data) {
        return webClient.post()
                .uri("/user")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorMap(throwable -> new RuntimeException("Failed to create user: " + throwable.getMessage()));
    }

    public Mono<User> login(LoginDTO data) {
        return webClient.post()
                .uri("/user/login")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorMap(throwable -> new RuntimeException("Login failed: " + throwable.getMessage()));
    }

    public Mono<User> getUserById(int id) {
        return webClient.get()
                .uri("/user/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorMap(throwable -> new RuntimeException("Failed to retrieve user by id: " + throwable.getMessage()));
    }

    public Mono<List<User>> getAllUsers() {
        return webClient.get()
                .uri("/user")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .onErrorMap(throwable -> new RuntimeException("Failed to retrieve users: " + throwable.getMessage()));
    }

    public Mono<Void> deleteUser(int id) {
        return webClient.delete()
                .uri("/user/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(throwable -> new RuntimeException("Failed to delete user: " + throwable.getMessage()))
                .then();
    }
}