package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.enuns.StatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PagamentoCartaoInputDTO extends PagamentoInputDTO{

    private String nomeTitular;
    private String numeroCartao;  // Armazenado de forma criptografada
    private String validade;
    private String cvv;  // Armazenado de forma criptografada

}
