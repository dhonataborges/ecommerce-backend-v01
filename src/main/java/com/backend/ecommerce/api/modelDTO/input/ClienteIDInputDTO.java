package com.backend.ecommerce.api.modelDTO.input;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteIDInputDTO {
    private Long id;
    private String nome;
    private String sobrenome;
}
