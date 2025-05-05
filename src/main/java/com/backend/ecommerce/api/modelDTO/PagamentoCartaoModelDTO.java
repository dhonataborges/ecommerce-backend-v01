package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class PagamentoCartaoModelDTO extends PagamentoModelDTO {
    private String nomeTitular;
    private String numeroCartaoMascarado;  // Armazenado de forma criptografada
    private String validade;

}
