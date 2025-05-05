package com.backend.ecommerce.domain.model;

import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa o pagamento de um pedido no sistema. A classe é mapeada para a tabela 'tb_pagamento' no banco de dados.
 * O pagamento pode ser realizado por diferentes métodos, como cartão, pix ou boleto, e está associado a um pedido específico.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pagamento") // Define a tabela no banco de dados
public class Pagamento {

    /**
     * Identificador único do pagamento.
     * Este campo é a chave primária da tabela 'tb_pagamento'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id com a estratégia de auto incremento
    private Long id;

    /**
     * Data em que o pagamento foi realizado.
     * Armazenada no formato de data (yyyy-MM-dd).
     */
    @Column(name = "data_pagamento") // Define o nome da coluna no banco de dados
    private LocalDate dataPagamento;

    /**
     * Valor do pagamento.
     * Armazena o valor total pago, sem considerar descontos ou parcelamentos.
     */
    @Column(nullable = false) // Define a coluna como obrigatória
    private BigDecimal valor;

    /**
     * Forma de pagamento utilizada.
     * Este campo está associado a uma instância da entidade 'FormaPagamento',
     * indicando o método de pagamento escolhido pelo cliente (ex: Cartão de Crédito, Pix, Boleto).
     */
    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id", nullable = false) // Associação com a tabela 'tb_forma_pagamento'
    private FormaPagamento formaPagamento;

    /**
     * Dados do pagamento via cartão de crédito.
     * Mapeado como um campo embutido, armazenando informações como o nome do titular, número do cartão, validade e CVV.
     */
    @Embedded // Marca a classe como embutida, sem tabela separada no banco
    private DadosCartao dadosCartao;

    /**
     * Dados do pagamento via Pix.
     * Mapeado como um campo embutido, armazenando a chave Pix.
     */
    @Embedded
    private DadosPix dadosPix;

    /**
     * Dados do pagamento via boleto bancário.
     * Mapeado como um campo embutido, armazenando o código do boleto gerado.
     */
    @Embedded
    private DadosBoleto dadosBoleto;

    /**
     * Status do pagamento.
     * Define o estado do pagamento, podendo ser 'Pendente', 'Aprovado', 'Recusado'.
     */
    @Enumerated(EnumType.STRING) // Utiliza o nome do enum para armazenar no banco, em vez de números
    @Column(name = "status") // Define o nome da coluna como 'status'
    private StatusPagamento status;

    /**
     * Pedido associado ao pagamento.
     * Um pagamento pode estar vinculado a um único pedido, sendo uma relação de um para um.
     */
    @OneToOne(mappedBy = "pagamento") // Mapeamento inverso de 'pagamento' em 'Pedido'
    private Pedido pedido;
}
