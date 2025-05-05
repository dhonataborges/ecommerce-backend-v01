package com.backend.ecommerce.model.enuns;

import lombok.Getter;

@Getter
public enum Permicoes {
    GENERICO(0,"Selecione"),
    CLIENTE(1,"Cliente"),
    ADMIN(2, "Admin");

    private Integer codPermicoes;
    private String descricao;

    private Permicoes(Integer codPermicoes, String descricao) {
        this.codPermicoes = codPermicoes;
        this.descricao = descricao;
    }

    public static Permicoes toEnum(Integer cod) {

        if(cod == null) {
            return null;
        }

        for(Permicoes x : Permicoes.values()) {
            if (cod.equals(x.getCodPermicoes())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Permição Invalida!" + cod);
    }
}
