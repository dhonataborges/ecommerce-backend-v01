package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.Pagamento;
import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PagamentoModelDTO {

    private Long id;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;

    private StatusPagamento status;

}
