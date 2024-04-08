package com.todolist.todolist.controller;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.service.TaskServiceIpml;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/task")
public class taskController {
    @Autowired
    TaskServiceIpml task_service;

    @GetMapping
    @Operation(summary = "Lista todas as tarefas da lista")
    public List<Task> getAllTasks(){
        return task_service.getTask();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Lista a tarefa da lista pelo id")
    public Task getTaskById(@PathVariable long id){
        return task_service.getTaskById(id);
    }

    @PostMapping
    @Operation(summary = "Adiciona uma tarefa a lista")
    public Task postTask(@RequestBody Task task){
        return task_service.postTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera uma tarefa da lista")
    public ResponseEntity<Task> putTask(@PathVariable int id, @RequestBody Task task){
        return task_service.putTask(id, task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta a tarefa da lista")
    public ResponseEntity<Object> deleteTask(@PathVariable long id){

        return task_service.deleteTask(id);
    }

}
