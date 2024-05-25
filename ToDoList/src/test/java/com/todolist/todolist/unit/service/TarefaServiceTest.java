package com.todolist.todolist.unit.service;


import com.todolist.todolist.TaskMock;
import com.todolist.todolist.dto.result;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.repository.TaskRepository;
import com.todolist.todolist.service.TaskServiceIpml;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
public class TarefaServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceIpml taskService;
    private Task tarefa;
    @BeforeEach
    void setUp() {
        tarefa = TaskMock.mockTask();
    }

    @Test
    void createTask() {
        when(taskRepository.save(tarefa)).thenReturn(tarefa);
        Task Tarefa = taskService.postTask(tarefa, new result());

        assertNotNull(Tarefa);
        assertEquals(tarefa.getTitulo(), Tarefa.getTitulo());
        verify(taskRepository, times(1)).save(tarefa);
    }

    @Test
    void listAllTask() {
        when(taskRepository.findAll()).thenReturn(TaskMock.mockListTask());
        var tarefas = taskService.getTask();
        assertNotNull(tarefas);
        assertEquals(tarefa.getTitulo(), tarefas.getFirst().getTitulo());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void findTaskById() {
        when(taskRepository.findById(1L)).thenReturn(TaskMock.mockOptionalTask());
        ResponseEntity<Task> foundTarefa = taskService.getTaskById(1L);

        assertNotNull(foundTarefa.getBody());
        assertEquals(tarefa.getTitulo(), foundTarefa.getBody().getTitulo());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void updateTaskById() {
        when(taskRepository.save(tarefa)).thenReturn(tarefa);
        result res = new result();
        ResponseEntity<Task> resultTarefa = taskService.putTask(1L, tarefa, res);

        assertNotNull(resultTarefa);
        assertEquals(tarefa.getTitulo(), resultTarefa.getBody().getTitulo());
        verify(taskRepository, times(1)).save(tarefa);
    }

    @Test
    void deleteTaskById() {
        when(taskRepository.findById(1L)).thenReturn(TaskMock.mockOptionalTask());
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void setStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(taskRepository.save(tarefa)).thenReturn(tarefa);

        ResponseEntity<Task> response = taskService.setStatus(1L, statusEnum.INICIALIZADA);

        assertEquals(statusEnum.INICIALIZADA, response.getBody().getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(tarefa);
    }

    @Test
    void alterarPrioridade() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(taskRepository.save(tarefa)).thenReturn(tarefa);

        ResponseEntity<Task> response = taskService.alterarPrioridade(1L, prioridadeEnum.ALTA);

        assertEquals(prioridadeEnum.ALTA, response.getBody().getPrioridade());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(tarefa);
    }
}