package com.todolist.todolist.dto;

import com.todolist.todolist.enums.tipoEnum;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class tipoTarefa {
    private int prazo;
    private LocalDate data;
    private int tipo;
}
