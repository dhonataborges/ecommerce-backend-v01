package com.backend.ecommerce.api.modelDTO.input;

import com.backend.ecommerce.domain.model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInputDTO {

    @Size(max = 6)
    private String codProd;
    private String nomeProd;
    private String marca;
    private String descricaoProduto;
    private Integer categoria;
    private String descriCatedoria;
    private FotoProdutoInputDTO foto;

}
