
package com.backend.ecommerce.api.modelDTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.ALWAYS) // Garante que valores nulos sejam serializados
public class ProdutoDoPedidoModelDTO {

    private Long id;
    private String codProd;
    private String nomeProd;
    private FotoProdutoModelDTO foto;

}

