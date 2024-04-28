package com.todolist.todolist.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum tipoEnum {
    DATA (1, "Data"),
    PRAZO (2, "Prazo"),
    LIVRE (3, "Livre");

    private int codigo;
    private String descricao;
    public static tipoEnum getStatusById(int id){
        return Arrays.stream(values()).filter(x->x.codigo == id).findFirst().orElse(null);
    }
    tipoEnum(int id, String descricao){
        this.codigo = id;
        this.descricao = descricao;
    }

}