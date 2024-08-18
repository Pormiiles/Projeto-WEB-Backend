package com.example.user.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.user.domain.config.SpringClient;
import com.example.user.domain.models.User;

import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Define para usar a porta especificada (8080)
public class SpringClientIntegrationTest {
    private SpringClient springClient;

    @BeforeEach
    public void setUp() {
        String baseUrl = "http://localhost:8080";
        springClient = new SpringClient(baseUrl);
    }

    @Test
    public void testGetAllUsers() {
        Mono<List<User>> response = springClient.getAllUsers();
        List<User> users = response.block();

        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
    }
}