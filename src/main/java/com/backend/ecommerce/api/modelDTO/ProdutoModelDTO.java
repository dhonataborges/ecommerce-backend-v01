
package com.backend.ecommerce.api.modelDTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.ALWAYS) // Garante que valores nulos sejam serializados
public class ProdutoModelDTO {

    private Long id;
    private String codProd;
    private String nomeProd;
    private String descricao;
    private Integer categoria;
    private String descriCatedoria;
    private FotoProdutoModelDTO foto;

}

