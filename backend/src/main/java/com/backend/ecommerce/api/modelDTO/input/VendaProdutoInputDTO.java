package com.backend.ecommerce.api.modelDTO.input;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VendaProdutoInputDTO {

    private Long id;

    private EstoqueIDInputDTO estoque;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Column(name = "num_parcela")
    private Integer numParcela;

    @Column(name = "desconto_porcento")
    private BigDecimal descontoPorcento;

}
