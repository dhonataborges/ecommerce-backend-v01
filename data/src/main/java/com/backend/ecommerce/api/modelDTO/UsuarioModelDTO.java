
package com.backend.ecommerce.api.modelDTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.ALWAYS) // Garante que valores nulos sejam serializados
public class UsuarioModelDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private Integer codPermicoes;
    private String descriPermicoes;
}

