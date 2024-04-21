package com.todolist.todolist.enums;

import java.util.Arrays;

public enum prioridadeEnum {
        ALTA (1, "Prioridade alta"),
        MEDIA (2, "Prioridade média"),
        BAIXA (3, "Prioridade baixa");

        private int id;
        private String descricao;

        prioridadeEnum(int id, String descricao){
            this.id = id;
            this.descricao = descricao;
        }

    public static prioridadeEnum getStatusById(int id){
        return Arrays.stream(values()).filter(x->x.id == id).findFirst().orElse(null);
    }

}
