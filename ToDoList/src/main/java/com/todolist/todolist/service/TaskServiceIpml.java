package com.todolist.todolist.service;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Task postTask(Task task){
        return task_repository.save(task);
    }
    public ResponseEntity<Task> putTask(long id, Task task){
        return task_repository.findById(id)
                .map(taskToUpdate ->{
                    taskToUpdate.setTitulo(task.getTitulo());
                    taskToUpdate.setDescription(task.getDescription());
                    taskToUpdate.setStatus(task.getStatus());
                    taskToUpdate.setDataInicio(task.getDataInicio());
                    taskToUpdate.setDataFim(task.getDataFim());
                    taskToUpdate.setComplete(task.getComplete());
                    Task updated = task_repository.save(taskToUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    public ResponseEntity<Object> deleteTask(long id){
        return task_repository.findById(id)
                .map(taskToDelete ->{
                    task_repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
