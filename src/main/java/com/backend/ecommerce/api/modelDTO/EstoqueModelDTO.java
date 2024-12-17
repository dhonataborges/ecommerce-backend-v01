
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class EstoqueModelDTO {

    private Long id;
    private ProdutoModelDTO produto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private BigDecimal valorUnid;
    private Integer qtdProd;
    private BigDecimal valorTotalProd;

}

