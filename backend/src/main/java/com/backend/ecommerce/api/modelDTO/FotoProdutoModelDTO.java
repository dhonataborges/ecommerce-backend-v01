
package com.backend.ecommerce.api.modelDTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModelDTO {
    private Long id;
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}

