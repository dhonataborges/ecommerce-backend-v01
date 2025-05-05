
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class EstoqueDoPedidoModelDTO {

    private Long id;
    private ProdutoModelDTO produto;
    private BigDecimal valorUnitario;
    private Integer quantidade;

}

