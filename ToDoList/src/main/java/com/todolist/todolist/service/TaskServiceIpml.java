package com.todolist.todolist.service;

import com.todolist.todolist.dto.result;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.repository.TaskRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceIpml {
    @Autowired
    TaskRepository task_repository;
    public List<Task> getTask(){
        return task_repository.findAll();
    }
    public Task getTaskById(long id){
        return task_repository.getById(id);
    }
    public Task postTask(Task task, result result){
        task.setCreatedAt(LocalDate.now());
        if(task.isTaskLivre()){
            if(validaParametros(task, result))
                return null;
        }else if(task.getPrazo() > 0 ){
            task.setDataFim(LocalDate.now().plusDays(task.getPrazo()));
        }else{
            if (validaDataFim(task, result)){
                return null;
            }
        }
        return task_repository.save(task);
    }

    public ResponseEntity<Task> putTask(long id, Task task, result result){
        if(task.isTaskLivre()){
            if(validaParametros(task, result))
                return null;
        }else if(task.getPrazo() > 0 ){
            task.setDataFim(LocalDate.now().plusDays(task.getPrazo()));
        }else {
            if (validaDataFim(task, result)){
                return null;
            }
        }
        return task_repository.findById(id)
                .map(taskToUpdate ->{
                    taskToUpdate.setTitulo(task.getTitulo());
                    taskToUpdate.setDescription(task.getDescription());
                    taskToUpdate.setStatus(task.getStatus());
                    taskToUpdate.setDataFim(task.getDataFim());
                    taskToUpdate.setCreatedAt(task.getCreatedAt());
                    taskToUpdate.setComplete(task.getComplete());
                    taskToUpdate.setTaskLivre(task.isTaskLivre());
                    taskToUpdate.setPrazo(task.getPrazo());
                    taskToUpdate.setPrioridade(task.getPrioridade());
                    Task updated = task_repository.save(taskToUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.ok().body(task_repository.save(task)));
    }
    private boolean validaParametros(Task task, result result) {
        if(task.getPrazo() > 0){
            result.setErro(true);
            result.setErroMensage("Não é possível informar um prazo para uma task livre.");
            return false;
        }
        if(task.getDataFim() != null){
            result.setErro(true);
            result.setErroMensage("Não é possível informar data para finalizar uma task livre.");
            return false;
        }
        return true;
    }
    private boolean validaDataFim(Task task, result result) {
        if(task.getDataFim().isBefore(LocalDate.now())){
            result.setErro(true);
            result.setErroMensage("A data de finalização da tarefa deve ser maior que a data de hoje.");
            return false;
        }else
            return true;
    }

    public ResponseEntity<Object> deleteTask(long id){
        return task_repository.findById(id)
                .map(taskToDelete ->{
                    task_repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Task> setStatus(long id, statusEnum statusById) {
        return task_repository.findById(id).map(taskToUpdate ->{
                    taskToUpdate.setStatus(statusById);
                    Task updated = task_repository.save(taskToUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    ResponseEntity<Task> alterarPrioridade(long id, prioridadeEnum prioridade){
        return task_repository.findById(id).map(taskToUpdate ->{
            taskToUpdate.setPrioridade(prioridade);
            Task updated = task_repository.save(taskToUpdate);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
