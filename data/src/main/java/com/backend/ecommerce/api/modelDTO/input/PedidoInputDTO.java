package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.api.modelDTO.EnderecoModelDTO;
import com.backend.ecommerce.api.modelDTO.UsuarioModelDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PedidoInputDTO {

    private UsuarioIDInputDTO usuario;
    private EnderecoIDInputDTO endereco;
    // private FormaPagamento formaPagamento;
    private LocalDate dataPedido;
    private Integer codStatus;
    private String descriStatus;


}
