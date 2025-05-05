package com.backend.ecommerce.api.modelDTO;

import com.backend.ecommerce.domain.model.Cidade;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoModelDTO {
    private Long id;
    private UsuarioModelDTO usuario;
    private String nomeDono;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;
    private CidadeModelDTO cidade;
    private String complemento;
}
