package com.todolist.todolist.repository;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.statusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(statusEnum status);
}
