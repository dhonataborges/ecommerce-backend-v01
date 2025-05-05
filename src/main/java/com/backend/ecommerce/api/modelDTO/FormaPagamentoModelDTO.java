package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.Pagamento;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class FormaPagamentoModelDTO {

    private Long id;
    private String tipo;
    private String descricao;

}
