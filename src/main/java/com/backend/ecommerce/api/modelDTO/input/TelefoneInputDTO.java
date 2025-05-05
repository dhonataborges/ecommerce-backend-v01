package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneInputDTO {

    private String nomeResponsavel;
    private String numeroTelefone;
    private ClienteIDInputDTO cliente;

}
