package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PedidoModelDTO {

    private Long id;
    private UsuarioModelDTO usuario;
    private EnderecoModelDTO endereco;
    private Pagamento pagamento;
    private LocalDate dataPedido;
    private Integer quantidadeItem;
    private BigDecimal valorTotal;

    private Integer codStatus;
    private String descriStatus;
    private List<PedidoItemModelDTO> pedidoItens;

}
