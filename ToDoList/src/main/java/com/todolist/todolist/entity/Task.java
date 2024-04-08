package com.todolist.todolist.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @Column(name = "dataInicio")
    private Date dataInicio;
    @Column(name = "dataFim")
    private Date dataFim;
    @Column(name = "Status")
    private int status;

    public boolean getComplete(){
        return complete;
    }

}
