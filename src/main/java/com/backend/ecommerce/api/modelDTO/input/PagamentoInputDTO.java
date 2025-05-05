package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public abstract class PagamentoInputDTO {
    private Long id;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;

    private StatusPagamento status;
}
