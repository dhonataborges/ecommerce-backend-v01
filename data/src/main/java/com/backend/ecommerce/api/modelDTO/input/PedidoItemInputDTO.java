package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.api.modelDTO.EstoqueModelDTO;
import com.backend.ecommerce.api.modelDTO.PedidoModelDTO;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PedidoItemInputDTO {

    private PedidoIDInputDTO pedido;
    private CatalogoProdutoIDInputDTO catalogo;
    private BigDecimal valorUnitario;
    private Integer quantidade;

}
