package com.backend.ecommerce.domain.model.enuns;

import lombok.Getter;

/**
 * Enum que representa as permissões de acesso no sistema.
 * Cada permissão possui um código único (codigo) e uma descrição associada.
 */
@Getter
public enum Permissoes {

    GENERICO(0, "Selecione"), // Permissão genérica, usada quando nenhuma permissão foi selecionada.
    CLIENTE(1, "Cliente"), // Permissão para usuários do tipo cliente.
    ADMIN(2, "Admin"); // Permissão para usuários do tipo administrador.

    private Integer codigo; // Código único da permissão.
    private String descricao; // Descrição da permissão.

    /**
     * Construtor privado que inicializa os valores do enum com o código e a descrição.
     *
     * @param codPermicoes Código da permissão.
     * @param descricao    Descrição associada à permissão.
     */
    private Permissoes(Integer codPermicoes, String descricao) {
        this.codigo = codPermicoes;
        this.descricao = descricao;
    }

    /**
     * Método para converter um código inteiro em uma instância do enum Permissoes.
     *
     * @param cod Código da permissão a ser convertido.
     * @return A instância do enum correspondente ao código.
     * @throws IllegalArgumentException Se o código não corresponder a nenhuma permissão.
     */
    public static Permissoes toEnum(Integer cod) {
        if (cod == null) {
            return null; // Retorna null se o código for nulo.
        }

        // Percorre todas as permissões e verifica se o código fornecido corresponde a algum valor.
        for (Permissoes x : Permissoes.values()) {
            if (cod.equals(x.getCodigo())) {
                return x; // Retorna a permissão correspondente ao código.
            }
        }

        // Lança uma exceção caso o código não corresponda a nenhuma permissão.
        throw new IllegalArgumentException("Permissão Invalida!" + cod);
    }
}
