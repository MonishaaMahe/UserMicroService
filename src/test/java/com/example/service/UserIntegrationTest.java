/*
package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.SampleApp;
import com.sample.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest(classes = {SampleApp.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("password");
        user.setEmail("john@example.com");

        webTestClient.post()
            .uri("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.username").isEqualTo("john");
    }

    @Test
    void testGetAllUsers() {
        webTestClient.get()
            .uri("/api/users")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray();
    }
}
*/
