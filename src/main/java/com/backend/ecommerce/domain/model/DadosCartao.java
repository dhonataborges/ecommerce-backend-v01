package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa os dados de um cartão de crédito utilizados no pagamento de uma compra.
 * Essa classe é embutida em outra entidade, ou seja, seus atributos serão persistidos
 * dentro de outra tabela.
 */
@Getter
@Setter
@Embeddable // Indica que esta classe pode ser incorporada em outras entidades.
public class DadosCartao {

    /**
     * Nome do titular do cartão de crédito.
     * Este campo será armazenado na coluna 'nome_titular' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "nome_titular")
    private String nomeTitular;

    /**
     * Número do cartão de crédito.
     * Este campo será armazenado na coluna 'numero_cartao' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "numero_cartao")
    private String numeroCartao;

    /**
     * Data de validade do cartão de crédito.
     * Este campo será armazenado na coluna 'validade' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "validade")
    private String validade;

    /**
     * Código de segurança (CVV) do cartão de crédito.
     * Este campo será armazenado na coluna 'cvv' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "cvv")
    private String cvv;
}
