package com.todolist.todolist.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum statusEnum {
    BACKLOG (0, "Em backlog"),
    INICIALIZADA (1, "Inicializada"),
    FINALIZADA (2, "Finalizada");

    private int id;
    private String descricao;
    public static statusEnum getStatusById(int id){
        return Arrays.stream(values()).filter(x->x.id == id).findFirst().orElse(null);
    }
    statusEnum(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

}
