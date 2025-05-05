package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa um item dentro de um pedido.
 * A classe é mapeada para a tabela 'tb_pedido_item' no banco de dados.
 * Cada instância dessa classe representa um produto que faz parte de um pedido.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedido_item") // Define a tabela no banco de dados
public class PedidoItem {

    /**
     * Identificador único do item do pedido.
     * Este campo é a chave primária da tabela 'tb_pedido_item'.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do id com a estratégia de auto incremento
    private Long id;

    /**
     * Pedido ao qual este item pertence.
     * Este campo representa o relacionamento 'Muitos para um' com a entidade 'Pedido'.
     * Cada item de pedido está associado a um pedido específico.
     */
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false) // Define a coluna 'pedido_id' no banco, que faz referência ao pedido
    private Pedido pedido;

    /**
     * Produto presente neste item do pedido.
     * Este campo representa o relacionamento 'Muitos para um' com a entidade 'CatalogoProduto',
     * que contém informações sobre o produto selecionado.
     */
    @ManyToOne
    @JoinColumn(name = "catalogo_id") // Define a coluna 'catalogo_id' no banco, que faz referência ao produto
    private CatalogoProduto catalogo;

    /**
     * Quantidade do produto deste item no pedido.
     * Representa a quantidade de unidades do produto que foi adquirido.
     */
    @Column(nullable = false) // Define a coluna no banco com a restrição de ser obrigatória
    private Integer quantidade;

    /**
     * Valor unitário do produto no momento da compra.
     * Armazenado no banco de dados como 'valor_unitario'.
     */
    @JoinColumn(name = "valor_unitario") // Define o nome da coluna no banco de dados
    private BigDecimal valorUnitario;

    /**
     * Valor total do item do pedido.
     * Calculado como: quantidade * valorUnitario.
     * Armazenado no banco de dados como 'valor_total'.
     */
    @Column(nullable = false) // Define a coluna no banco com a restrição de ser obrigatória
    private BigDecimal valorTotal;

}
