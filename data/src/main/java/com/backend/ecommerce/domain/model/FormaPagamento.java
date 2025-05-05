package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa a forma de pagamento utilizada no sistema. A classe é mapeada para a tabela 'tb_forma_pagamento' no banco de dados.
 * Cada forma de pagamento possui um tipo (ex: cartão de crédito, boleto, etc.) e uma descrição opcional.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_forma_pagamento") // Define a tabela no banco de dados
public class FormaPagamento {

    /**
     * Identificador único da forma de pagamento.
     * A coluna 'id' é a chave primária na tabela 'tb_forma_pagamento'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID pelo banco de dados
    private Long id;

    /**
     * Tipo da forma de pagamento (ex: "Cartão de Crédito", "Boleto").
     * A coluna não pode ser nula e tem comprimento máximo de 50 caracteres.
     */
    @Column(nullable = false, length = 50) // Define as restrições de nulidade e comprimento da coluna
    private String tipo;

    /**
     * Descrição detalhada da forma de pagamento (opcional).
     * A coluna tem comprimento máximo de 255 caracteres.
     */
    @Column(length = 255) // Define o comprimento máximo da coluna
    private String descricao;
}
