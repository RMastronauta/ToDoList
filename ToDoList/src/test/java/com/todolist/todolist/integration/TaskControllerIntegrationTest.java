package com.todolist.todolist.integration;

import com.todolist.todolist.TodolistApplication;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.tipoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;


@SpringBootTest(classes = TodolistApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class TarefasApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {

    }

    @Test
    void testeCreateTodoSucesso() {
        var todo = new Task(1,"Titulo", "todo lista", false, LocalDate.now(), LocalDate.now(), 1, prioridadeEnum.BAIXA, tipoEnum.PRAZO);

        webTestClient
                .post()
                .uri("/task/v1")
                .bodyValue(todo)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.titulo").isEqualTo(todo.getTitulo())
                .jsonPath("$.description").isEqualTo(todo.getDescription())
                .jsonPath("$.complete").isEqualTo(todo.getComplete());
    }

    @Test
    void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        webTestClient.get()
                .uri("/task/v1")
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void testeUpdateTodoSucesso() {
        var updatedTodo = new Task(1, "Titulo", "todo lista", false, LocalDate.now(), LocalDate.now(), 1, prioridadeEnum.BAIXA, tipoEnum.PRAZO);

        webTestClient
                .put()
                .uri("/task/v1/1")  // Assume que estamos atualizando a tarefa com ID 1
                .bodyValue(updatedTodo)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.titulo").isEqualTo(updatedTodo.getTitulo())
                .jsonPath("$.description").isEqualTo(updatedTodo.getDescription())
                .jsonPath("$.complete").isEqualTo(updatedTodo.getComplete())
                .jsonPath("$.prioridade").isEqualTo(updatedTodo.getPrioridade().toString());
    }
    @Test
    void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        webTestClient.get()
                .uri("/task/v1/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.titulo").isEqualTo("Titulo")
                .jsonPath("$.description").isEqualTo("todo lista")
                .jsonPath("$.complete").isEqualTo("false")
                .jsonPath("$.prioridade").isEqualTo("BAIXA");
    }

}
