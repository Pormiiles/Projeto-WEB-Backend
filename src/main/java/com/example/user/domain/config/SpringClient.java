package com.example.user.domain.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.user.domain.models.User;

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

    // Método para buscar todos os usuários. Envia um GET request.
    public Mono<List<User>> getAllUsers() {
        return webClient.get() // Define o método HTTP como GET.
                .uri("/user") // Define o endpoint da API para buscar todos os usuários.
                .retrieve() // Faz a requisição e espera uma resposta.
                .bodyToFlux(User.class) // Converte o corpo da resposta para um Fluxo de objetos do tipo User.
                .collectList() // Coleta os elementos do Fluxo em uma Lista.
                .onErrorMap(throwable -> new RuntimeException("Failed to retrieve users: " + throwable.getMessage())); // Mapeia erros para uma exceção genérica de runtime.
    }
}