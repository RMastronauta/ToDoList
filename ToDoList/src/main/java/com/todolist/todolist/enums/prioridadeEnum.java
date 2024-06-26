package com.todolist.todolist.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum prioridadeEnum {
        ALTA (0, "Prioridade alta"),
        MEDIA (1, "Prioridade média"),
        BAIXA (2, "Prioridade baixa");

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
