package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDTO {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Integer codPermissoes;
    private String descriPermissoes;

}
