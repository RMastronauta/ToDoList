package com.todolist.todolist.controller;

import com.todolist.todolist.dto.result;
import com.todolist.todolist.dto.tipoTarefa;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.tipoEnum;
import com.todolist.todolist.interfaces.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class taskController {
    @Autowired
    ITaskService task_service;

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
    public ResponseEntity postTask(@RequestBody Task task){

        var result = new result();
        Task tarefa =  task_service.postTask(task, result);
        if(result.isErro())
            return ResponseEntity.badRequest().body(result.getErroMensage());
        return ResponseEntity.ok().body(tarefa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera uma tarefa da lista")
    public ResponseEntity putTask(@PathVariable int id, @RequestBody Task task){
        var result = new result();
        var tarefa =  task_service.putTask(id, task, result);
        if(result.isErro())
            return ResponseEntity.badRequest().body(result.getErroMensage());
        return tarefa;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta a tarefa da lista")
    public ResponseEntity<Object> deleteTask(@PathVariable long id){
        return task_service.deleteTask(id);
    }
    @GetMapping("/v1")
    @Operation(summary = "Lista todas as tarefas da lista")
    public List<Task> getAllTasksV1(){
        return task_service.getTask();
    }
    @GetMapping("/v1/{id}")
    @Operation(summary = "Lista a tarefa da lista pelo id")
    public Task getTaskByIdV1(@PathVariable long id){
        return task_service.getTaskById(id);
    }

    @PostMapping("/v1")
    @Operation(summary = "Adiciona uma tarefa a lista")
    public ResponseEntity postTaskV1(@RequestBody Task task){
        var result = new result();
        Task tarefa =  task_service.postTask(task, result);
        if(result.isErro())
            return ResponseEntity.badRequest().body(result.getErroMensage());
        return ResponseEntity.ok().body(tarefa);
    }

    @PutMapping("/v1/{id}")
    @Operation(summary = "Altera uma tarefa da lista")
    public ResponseEntity putTaskV1(@PathVariable int id, @RequestBody Task task){
        var result = new result();
        var tarefa =  task_service.putTask(id, task, result);
        if(result.isErro())

            return ResponseEntity.badRequest().body(result.getErroMensage());
        return tarefa;
    }

    @DeleteMapping("/v1/{id}")
    @Operation(summary = "Deleta a tarefa da lista")
    public ResponseEntity<Object> deleteTaskV1(@PathVariable long id){

        return task_service.deleteTask(id);
    }
    @PatchMapping("/v1/status/{id}/{status}")
    public ResponseEntity<Task> setStatus(@PathVariable long id, @PathVariable int value){
        return task_service.setStatus(id, statusEnum.getStatusById(value));
    }
    @PatchMapping("/v1/prioridade/{id}/{status}")
    public ResponseEntity<Task> alterarPrioridade(@PathVariable long id, @PathVariable int value){
        return task_service.alterarPrioridade(id, prioridadeEnum.getStatusById(value));
    }
    @PatchMapping("/v1/complete/{id}/{status}")
    public ResponseEntity<Task> setComplete(@PathVariable long id, @PathVariable boolean value){
        return task_service.setComplete(id, value);
    }
    @PatchMapping("/v1/tipoTarefa/{id}/{tipoTarefa}")
    public ResponseEntity<Task> setTipoTarefa(@PathVariable long id, @PathVariable tipoTarefa value){
        return task_service.setTipoTarefa(id, value);
    }
}
