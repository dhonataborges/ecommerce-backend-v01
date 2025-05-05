package com.backend.ecommerce.domain.model.enuns;

import lombok.Getter;

/**
 * Enum que representa os status de pagamento de um pedido no sistema.
 * Cada status possui um código único (codStatus) e uma descrição associada.
 */
@Getter
public enum StatusPagamento {

    PENDENTE(0, "Pendente"),  // Status quando o pagamento ainda está pendente.
    APROVADO(1, "Aprovado"),  // Status quando o pagamento foi aprovado.
    RECUSADO(2, "Recusado");  // Status quando o pagamento foi recusado.

    private Integer codStatus;  // Código único para cada status de pagamento.
    private String descricao;   // Descrição do status de pagamento.

    /**
     * Construtor privado para inicializar os valores do enum com o código e a descrição.
     *
     * @param codStatus Código do status de pagamento.
     * @param descricao Descrição associada ao status de pagamento.
     */
    private StatusPagamento(Integer codStatus, String descricao) {
        this.codStatus = codStatus;
        this.descricao = descricao;
    }

    /**
     * Método para converter um código inteiro em uma instância do enum StatusPagamento.
     *
     * @param cod Código do status a ser convertido.
     * @return A instância do enum correspondente ao código fornecido.
     * @throws IllegalArgumentException Se o código não corresponder a nenhum status.
     */
    public static StatusPagamento toEnum(Integer cod) {
        if (cod == null) {
            return null;  // Retorna null se o código fornecido for nulo.
        }

        // Percorre todos os status de pagamento e verifica se o código fornecido corresponde a algum status.
        for (StatusPagamento x : StatusPagamento.values()) {
            if (cod.equals(x.getCodStatus())) {
                return x;  // Retorna o status correspondente ao código.
            }
        }

        // Lança uma exceção caso o código não corresponda a nenhum status.
        throw new IllegalArgumentException("Status Invalido!" + cod);
    }
}
