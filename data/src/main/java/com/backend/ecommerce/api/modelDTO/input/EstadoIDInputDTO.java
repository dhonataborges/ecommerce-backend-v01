package com.backend.ecommerce.api.modelDTO.input;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIDInputDTO {
    private Long id;
    private String nome;
}
