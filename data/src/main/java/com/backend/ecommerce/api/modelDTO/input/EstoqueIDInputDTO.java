package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueIDInputDTO {
    private Long id;
    private ProdutoIDInputDTO produto;
}
