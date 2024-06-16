package com.todolist.todolist.service;

import com.todolist.todolist.dto.result;
import com.todolist.todolist.dto.tipoTarefa;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.tipoEnum;
import com.todolist.todolist.interfaces.ITaskService;
import com.todolist.todolist.repository.TaskRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceIpml  implements ITaskService {
    @Autowired
    TaskRepository task_repository;
    @Override
    public List<Task> getTask(){
        return task_repository.findAll();
    }
    @Override
    public ResponseEntity<Task> getTaskById(long id){
        return  task_repository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }
    @Override
    public Task postTask(Task task, result result){
        task.setCreatedAt(LocalDate.now());

        if(task.getTipoTarefa().getCodigo() == 2){ //Task livre
            if(!validaParametros(task, result))
                return null;
        }else if(task.getTipoTarefa().getCodigo() == 1 && task.getPrazo() > 0){
            task.setDataFim(LocalDate.now().plusDays(task.getPrazo()));
        }else{
            if (task.getTipoTarefa().getCodigo() == 0 && !validaDataFim(task, result)){
                return null;
            }
        }
        return task_repository.save(task);
    }
    @Override
    public ResponseEntity<Task> putTask(long id, Task task, result result){
        if(task.getTipoTarefa().getCodigo() == 3){ //Task livre
            if(validaParametros(task, result))
                return null;
        }else if(task.getTipoTarefa().getCodigo() == 2 && task.getPrazo() > 0){
            task.setDataFim(LocalDate.now().plusDays(task.getPrazo()));
        }else{
            if (task.getTipoTarefa().getCodigo() == 1 && !validaDataFim(task, result)){
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
                    taskToUpdate.setTipoTarefa(task.getTipoTarefa());
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
    @Override
    public ResponseEntity<Object> deleteTask(long id){
        return task_repository.findById(id)
                .map(taskToDelete ->{
                    task_repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
    @Override
    public ResponseEntity<Task> setStatus(long id, statusEnum statusById) {
        Task task = task_repository.getById(id);
        task.setStatus(statusById);
        Task updated = task_repository.save(task);
        return updated != null ? ResponseEntity.ok().body(updated) : ResponseEntity.notFound().build();
    }
    @Override
    public ResponseEntity<Task> alterarPrioridade(long id, prioridadeEnum prioridade){
        return task_repository.findById(id).map(taskToUpdate ->{
            taskToUpdate.setPrioridade(prioridade);
            Task updated = task_repository.save(taskToUpdate);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
    @Override
    public ResponseEntity<Task> setComplete(long id, boolean complete){
        return task_repository.findById(id).map(taskToUpdate ->{
            taskToUpdate.setComplete(complete);
            Task updated = task_repository.save(taskToUpdate);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Task> setTipoTarefa(long id, tipoTarefa statusById) {
        Task task = task_repository.getById(id);
        setParametrosTipoTarefa(task, statusById);
        Task updated = task_repository.save(task);
        return updated != null ? ResponseEntity.ok().body(updated) : ResponseEntity.notFound().build();
    }

    @Override
    public List<Task> getTaskByStatusV1(statusEnum status) {
        return task_repository.findAllByStatus(status);
    }

    private void setParametrosTipoTarefa(Task task, tipoTarefa statusById) {
        if(statusById.getTipo() == tipoEnum.LIVRE.getCodigo()) { //Task livre
            task.setPrazo(0);
            task.setDataFim(null);
        }else if(statusById.getTipo() == tipoEnum.PRAZO.getCodigo() && statusById.getPrazo() > 0){
            task.setDataFim(LocalDate.now().plusDays(statusById.getPrazo()));
            task.setPrazo(statusById.getPrazo());
        }else if(statusById.getTipo() == tipoEnum.DATA.getCodigo() && statusById.getData() != null){
            task.setDataFim(statusById.getData());
        }
    }
}
