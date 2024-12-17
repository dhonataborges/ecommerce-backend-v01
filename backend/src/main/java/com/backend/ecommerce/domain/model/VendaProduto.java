package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_venda_produto")
public class VendaProduto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = {"dataEntrada, valorUnid, qtdProd, valorTotalProd"}, allowGetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Column(name = "num_parcela")
    private Integer numParcela;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "desconto_porcento")
    private BigDecimal descontoPorcento;
}
