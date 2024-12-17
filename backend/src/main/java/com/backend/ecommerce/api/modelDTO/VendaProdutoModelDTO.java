
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class VendaProdutoModelDTO {

    private Long id;
    private EstoqueModelDTO estoque;
    private BigDecimal valorVenda;
    private Integer numParcela;
    private BigDecimal valorParcela;
    private BigDecimal descontoPorcento;

}

