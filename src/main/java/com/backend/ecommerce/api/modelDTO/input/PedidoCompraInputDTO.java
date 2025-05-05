package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PedidoCompraInputDTO {
    private Long clienteId;
    private Long vendaId;
    private Integer quantidade = 1; // padrão 1, mas pode vir da requisição

}
