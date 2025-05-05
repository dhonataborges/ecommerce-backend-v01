package com.backend.ecommerce.model;/*
package com.backend.ecommerce.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedido_todos_clientes")
public class PedidoTodosClientes {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_pedido")
    private String codPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "qtd_pedido")
    private Integer qtdPedido;

    @Column(name = "valor_total_pedidos")
    private BigDecimal valorTotalPedidos;
}
*/
