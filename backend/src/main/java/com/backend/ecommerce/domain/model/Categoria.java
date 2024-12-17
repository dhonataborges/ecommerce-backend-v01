package com.backend.ecommerce.domain.model;

import lombok.Getter;

@Getter
public enum Categoria {
    GENERICO(0,"Selecione"),
    PERFUMARIA(1,"Perfumaria"),
    COPOR_E_BANHO(2, "Corpo e Banho"),
    CABELOS(3, "Cabelos"),
    ROSTO(4, "Rosto"),
    MAQUIAGEM(5, "Maquiagem"),
    INFANTIL(6,"Infantil");

    private Integer codCategoria;
    private String descricao;

    private Categoria(Integer codCategoria, String descricao) {
        this.codCategoria = codCategoria;
        this.descricao = descricao;
    }

    public static Categoria toEnum(Integer cod) {

        if(cod == null) {
            return null;
        }

        for(Categoria x : Categoria.values()) {
            if (cod.equals(x.getCodCategoria())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Categoria Invalida!" + cod);
    }
}
