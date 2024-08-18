package com.example.user.domain.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.user.domain.models.LoginDTO;
import com.example.user.domain.models.User;
import com.example.user.domain.models.UserCreateDTO;

import reactor.core.publisher.Mono;

@Service // Anotação que indica que esta classe é um serviço Spring, que contém a lógica de negócios da aplicação.
public class SpringClient {

    // Cliente WebClient utilizado para fazer requisições HTTP no contexto do Spring WebFlux.
    private final WebClient webClient;

    // Construtor que inicializa o WebClient com a URL base e um cabeçalho padrão para todas as requisições.
    public SpringClient(@Value("${api.base.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl) // Define a URL base para as requisições a partir do valor configurado.
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Define o cabeçalho Content-Type para JSON.
                .build(); // Constrói a instância de WebClient.
    }

    // Método para criar um novo usuário. Envia um POST request com os dados do usuário.
    public Mono<User> createUser(UserCreateDTO data) {
        return webClient.post() // Define o método HTTP como POST.
                .uri("/user") // Define o endpoint da API para criar um usuário.
                .bodyValue(data) // Envia os dados do DTO no corpo da requisição.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToMono(User.class) // Converte o corpo da resposta para um objeto do tipo User.
                .onErrorMap(throwable -> new RuntimeException("Failed to create user: " + throwable.getMessage())); // Mapeia erros para uma exceção genérica de runtime.
    }

    // Método para realizar o login. Envia um POST request com os dados de login.
    public Mono<User> login(LoginDTO data) {
        return webClient.post() // Define o método HTTP como POST.
                .uri("/user/login") // Define o endpoint da API para login.
                .bodyValue(data) // Envia os dados do DTO no corpo da requisição.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToMono(User.class) // Converte o corpo da resposta para um objeto do tipo User.
                .onErrorMap(throwable -> new RuntimeException("Login failed: " + throwable.getMessage())); // Mapeia erros para uma exceção genérica de runtime.
    }

    // Método para buscar um usuário por ID. Envia um GET request com o ID do usuário como parâmetro.
    public Mono<User> getUserById(int id) {
        return webClient.get() // Define o método HTTP como GET.
                .uri("/user/{id}", id) // Define o endpoint da API para buscar um usuário pelo ID, substituindo {id} pelo valor do parâmetro.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToMono(User.class) // Converte o corpo da resposta para um objeto do tipo User.
                .onErrorMap(throwable -> new RuntimeException("Failed to retrieve user by id: " + throwable.getMessage())); // Mapeia erros para uma exceção genérica de runtime.
    }

    // Método para buscar todos os usuários. Envia um GET request.
    public Mono<List<User>> getAllUsers() {
        return webClient.get() // Define o método HTTP como GET.
                .uri("/user") // Define o endpoint da API para buscar todos os usuários.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToFlux(User.class) // Converte o corpo da resposta para um Fluxo de objetos do tipo User.
                .collectList() // Coleta os elementos do Fluxo em uma Lista.
                .onErrorMap(throwable -> new RuntimeException("Failed to retrieve users: " + throwable.getMessage())); // Mapeia erros para uma exceção genérica de runtime.
    }

    // Método para deletar um usuário pelo ID. Envia um DELETE request com o ID do usuário como parâmetro.
    public Mono<Void> deleteUser(int id) {
        return webClient.delete() // Define o método HTTP como DELETE.
                .uri("/user/{id}", id) // Define o endpoint da API para deletar um usuário pelo ID, substituindo {id} pelo valor do parâmetro.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToMono(Void.class) // Converte o corpo da resposta para um objeto do tipo Void, já que não se espera um corpo na resposta.
                .onErrorMap(throwable -> new RuntimeException("Failed to delete user: " + throwable.getMessage())) // Mapeia erros para uma exceção genérica de runtime.
                .then(); // Finaliza a cadeia reativa e retorna um Mono<Void>.
    }
}