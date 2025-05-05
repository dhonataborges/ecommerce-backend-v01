package com.backend.ecommerce.api.modelDTO.input;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInputDTO {
    private Long id;
    private String tipo;
    private String descricao;
}
