package com.backend.ecommerce.api.modelDTO.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EstoqueInputDTO {

    private ProdutoIDInputDTO produto;

    @Column(name = "data_entrada")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataEntrada = LocalDate.now();

    @PositiveOrZero
    @Column(name = "valor_und")
    private BigDecimal valorUnid;

    @Column(name = "qtd_prod")
    private Integer qtdProd;
}
