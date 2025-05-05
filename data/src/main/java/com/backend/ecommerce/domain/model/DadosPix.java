package com.backend.ecommerce.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa os dados de pagamento via Pix, incluindo a chave Pix associada à transação.
 * Esta classe é embutida em outra entidade, ou seja, seus atributos serão persistidos
 * dentro de outra tabela.
 */
@Getter
@Setter
@Embeddable // Indica que esta classe pode ser incorporada em outras entidades.
public class DadosPix {

    /**
     * Chave Pix utilizada para realizar a transação de pagamento.
     * Este campo será armazenado na coluna 'chave_pix' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "chave_pix")
    private String chavePix;
}
