package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Representa um produto no catálogo do sistema e-commerce.
 * Contém informações sobre preço, descontos, parcelas e o estoque ao qual o produto pertence.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_catalogo_produto")
public class CatalogoProduto {

    /**
     * Identificador único do produto no catálogo.
     * A chave primária é gerada automaticamente pelo banco de dados.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento ManyToOne com a entidade Estoque.
     * Um produto no catálogo está associado a um único estoque.
     * O carregamento do estoque é feito de forma preguiçosa (Lazy).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    /**
     * Valor do produto após a aplicação de descontos, se houver.
     * Este campo pode ser calculado com base no valor original do produto e no desconto aplicado.
     */
    @Column(name = "valor_com_desconto")
    private BigDecimal valorComDesconto;

    /**
     * Número de parcelas permitidas para o pagamento do produto.
     * Representa a quantidade de parcelas disponíveis para financiamento ou pagamento a prazo.
     */
    @Column(name = "num_parcela")
    private Integer numParcela;

    /**
     * Valor de cada parcela do pagamento do produto, caso seja parcelado.
     * Esse valor é calculado com base no valor com desconto e no número de parcelas.
     */
    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    /**
     * Percentual de desconto aplicado ao produto.
     * Esse valor é usado para calcular o valor com desconto e, consequentemente, o valor das parcelas.
     */
    @Column(name = "desconto_porcento")
    private BigDecimal descontoPorcento;

}
