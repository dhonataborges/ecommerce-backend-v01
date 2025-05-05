package com.backend.ecommerce.domain.model.enuns;

import lombok.Getter;

/**
 * Enum que representa as categorias de produtos no sistema de e-commerce.
 * Cada categoria tem um código único (codCategoria) e uma descrição associada.
 */
@Getter
public enum Categoria {

    GENERICO(0, "Selecione"), // Categoria genérica, usada quando nenhuma categoria foi selecionada.
    PERFUMARIA(1, "Perfumaria"), // Categoria para produtos de perfumaria.
    COPOR_E_BANHO(2, "Corpo e Banho"), // Categoria para produtos de corpo e banho.
    CABELOS(3, "Cabelos"), // Categoria para produtos relacionados a cabelos.
    ROSTO(4, "Rosto"), // Categoria para produtos para cuidados com o rosto.
    MAQUIAGEM(5, "Maquiagem"), // Categoria para produtos de maquiagem.
    INFANTIL(6, "Infantil"); // Categoria para produtos infantis.

    private Integer codCategoria; // Código único da categoria.
    private String descricao; // Descrição da categoria.

    /**
     * Construtor privado que inicializa os valores do enum com o código e descrição.
     *
     * @param codCategoria Código da categoria.
     * @param descricao    Descrição associada à categoria.
     */
    private Categoria(Integer codCategoria, String descricao) {
        this.codCategoria = codCategoria;
        this.descricao = descricao;
    }

    /**
     * Método para converter um código inteiro em uma instância do enum Categoria.
     *
     * @param cod Código da categoria a ser convertido.
     * @return A instância do enum correspondente ao código.
     * @throws IllegalArgumentException Se o código não corresponder a nenhuma categoria.
     */
    public static Categoria toEnum(Integer cod) {
        if (cod == null) {
            return null; // Retorna null se o código for nulo.
        }

        // Percorre todas as categorias e verifica se o código fornecido corresponde a algum valor.
        for (Categoria x : Categoria.values()) {
            if (cod.equals(x.getCodCategoria())) {
                return x; // Retorna a categoria correspondente ao código.
            }
        }

        // Lança uma exceção caso o código não corresponda a nenhuma categoria.
        throw new IllegalArgumentException("Categoria Invalida!" + cod);
    }
}
