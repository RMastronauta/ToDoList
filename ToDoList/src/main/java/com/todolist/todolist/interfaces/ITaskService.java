package com.todolist.todolist.interfaces;

import com.todolist.todolist.dto.result;
import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.statusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskService {
    public List<Task> getTask();
    public Task getTaskById(long id);
    public Task postTask(Task task, result result);
    public ResponseEntity<Task> putTask(long id, Task task, result result);
    public ResponseEntity<Object> deleteTask(long id);
    public ResponseEntity<Task> setStatus(long id, statusEnum statusById);
}
