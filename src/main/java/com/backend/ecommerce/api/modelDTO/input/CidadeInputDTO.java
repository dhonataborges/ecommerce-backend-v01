package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {

    @Column(nullable = false)
    private String nome;
    private EstadoIDInputDTO estado;

}
