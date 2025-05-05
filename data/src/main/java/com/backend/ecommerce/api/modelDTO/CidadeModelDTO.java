package com.backend.ecommerce.api.modelDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeModelDTO {
    private Long id;
    private String nome;
    private EstadoModelDTO estado;
}
