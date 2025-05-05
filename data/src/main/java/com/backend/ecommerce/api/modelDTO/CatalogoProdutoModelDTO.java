
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CatalogoProdutoModelDTO {
    private Long id;
    private EstoqueDoPedidoModelDTO estoque;
    private BigDecimal valorItemComDesconto;
    private Integer numParcela;
    private BigDecimal valorParcela;
    private BigDecimal descontoPorcento;
}

