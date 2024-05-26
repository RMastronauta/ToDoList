package com.todolist.todolist;

import com.todolist.todolist.entity.Task;
import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.tipoEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Simple.of;

public class TaskMock {
    public static Optional<Task> mockOptionalTask() {
        return Optional.of(mockTask());
    }

    public static Task mockTask() {
        return new Task(1, "TesteTitulo", "Descricao Teste", false, LocalDate.now(), LocalDate.now(), 2,prioridadeEnum.MEDIA, tipoEnum.PRAZO);
    }

    public static List<Task> mockListTask() {
        return List.of(mockTask());
    }
}
