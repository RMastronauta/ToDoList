package com.todolist.todolist.interfaces;

import com.todolist.todolist.dto.result;
import com.todolist.todolist.dto.tipoTarefa;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.enums.tipoEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskService {
    List<Task> getTask();
    ResponseEntity<Task> getTaskById(long id);
    Task postTask(Task task, result result);
    ResponseEntity<Task> putTask(long id, Task task, result result);
    ResponseEntity<Object> deleteTask(long id);
    ResponseEntity<Task> setStatus(long id, statusEnum statusById);
    ResponseEntity<Task> alterarPrioridade(long id, prioridadeEnum prioridade);
    ResponseEntity<Task> setComplete(long id, boolean complete);

    ResponseEntity<Task> setTipoTarefa(long id, tipoTarefa statusById);

    List<Task> getTaskByStatusV1(statusEnum status);
}
