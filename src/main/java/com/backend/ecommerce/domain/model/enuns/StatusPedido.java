package com.backend.ecommerce.domain.model.enuns;

import lombok.Getter;

/**
 * Enum que representa os status de um pedido no sistema de e-commerce.
 * Cada status possui um código único (codStatus) e uma descrição associada.
 */
@Getter
public enum StatusPedido {

    ABERTO(0, "Aberto"),   // Status quando o pedido foi criado, mas ainda não foi processado.
    PENDENTE(1, "Pendente"),  // Status quando o pedido está aguardando algum processo, como pagamento ou aprovação.
    FECHADO(2, "Fechado");  // Status quando o pedido foi finalizado e não pode mais ser modificado.

    private Integer codStatus;  // Código único para cada status de pedido.
    private String descricao;   // Descrição associada ao status do pedido.

    /**
     * Construtor privado para inicializar os valores do enum com o código e a descrição.
     *
     * @param codStatus Código do status do pedido.
     * @param descricao Descrição associada ao status do pedido.
     */
    private StatusPedido(Integer codStatus, String descricao) {
        this.codStatus = codStatus;
        this.descricao = descricao;
    }

    /**
     * Método para converter um código inteiro em uma instância do enum StatusPedido.
     *
     * @param cod Código do status a ser convertido.
     * @return A instância do enum correspondente ao código fornecido.
     * @throws IllegalArgumentException Se o código não corresponder a nenhum status.
     */
    public static StatusPedido toEnum(Integer cod) {
        if (cod == null) {
            return null;  // Retorna null se o código fornecido for nulo.
        }

        // Percorre todos os status de pedido e verifica se o código fornecido corresponde a algum status.
        for (StatusPedido x : StatusPedido.values()) {
            if (cod.equals(x.getCodStatus())) {
                return x;  // Retorna o status correspondente ao código.
            }
        }

        // Lança uma exceção caso o código não corresponda a nenhum status.
        throw new IllegalArgumentException("Status Invalido!" + cod);
    }
}
