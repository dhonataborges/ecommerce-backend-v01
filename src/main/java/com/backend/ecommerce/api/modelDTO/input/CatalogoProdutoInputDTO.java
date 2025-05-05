package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CatalogoProdutoInputDTO {
    private Long id;
    private EstoqueIDInputDTO estoque;
    private Integer numParcela;
    private BigDecimal descontoPorcento;
}
