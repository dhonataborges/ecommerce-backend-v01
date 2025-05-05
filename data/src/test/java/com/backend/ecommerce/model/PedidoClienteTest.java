package com.backend.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedido_cliente")
public class PedidoClienteTest {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qtd_Unid_produto")
    private Integer qtdUnidProduto;

    @Column(name = "qtd_itens")
    private Integer qtdItens;

    @Column(name = "valor_total_itens")
    private BigDecimal valorTotalItens;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_produto_id")
    private VendaProduto vendaProduto;

}
