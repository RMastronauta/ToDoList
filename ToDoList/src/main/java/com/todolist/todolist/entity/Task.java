package com.todolist.todolist.entity;

import com.todolist.todolist.enums.statusEnum;
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

    public boolean getComplete(){
        return complete;
    }

}
