package com.backend.ecommerce.api.modelDTO.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputDTO {

    private UsuarioIDInputDTO usuario;
    private String nomeDono;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;
    private CidadeIDInputDTO cidade;
    private String complemento;

}
