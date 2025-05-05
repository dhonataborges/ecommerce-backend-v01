package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.CatalogoProduto;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Pedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PedidoItemModelDTO {

    private Long id;
    private PedidoModelDTO pedido;
    private CatalogoProdutoDoPedidoModelDTO catalogo;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

}
