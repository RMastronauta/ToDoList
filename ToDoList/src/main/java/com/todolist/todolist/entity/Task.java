package com.todolist.todolist.entity;

import com.todolist.todolist.enums.prioridadeEnum;
import com.todolist.todolist.enums.statusEnum;
import com.todolist.todolist.enums.tipoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10")
    private int id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descricao")
    private String description;
    @Column(name = "complete")
    private boolean complete = false;
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @Column(name = "dataFim")
    private LocalDate dataFim;
    @Column(name = "Status")
    @Enumerated(EnumType.ORDINAL)
    private statusEnum status;
    @Column(name = "isTaskLivre")
    private boolean isTaskLivre;
    @Column(name = "prazo")
    private int prazo;
    @Column(name = "prioridade")
    @Enumerated(EnumType.ORDINAL)
    private prioridadeEnum prioridade;
    @Column(name = "tipoTarefa")
    @Enumerated(EnumType.ORDINAL)
    private tipoEnum tipoTarefa;

    public Task(int  id, String titulo, String descricao, boolean isCompleto, LocalDate CreatedAt, LocalDate dataFim, int prazoDias, prioridadeEnum prioridadeEnum, tipoEnum tipoTarefa) {
        this.id = id;
        this.titulo = titulo;
        this.description = descricao;
        this.complete = isCompleto;
        this.createdAt = CreatedAt;
        this.dataFim = dataFim;
        this.prazo = prazoDias;
        this.prioridade = prioridadeEnum;
        this.tipoTarefa = tipoTarefa;
    }

    public boolean getComplete(){
        return complete;
    }
}
