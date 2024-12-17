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

    @Column(name = "cod_prod")
    @Size(max = 6)
    private String codProd;

    @Column(name = "nome_prod")
    private String nomeProd;

    @Column(name = "descricao")
    private String descricao;

    @JoinColumn(name = "categoria")
    private Integer categoria;

    @JoinColumn(name = "descri_categoria")
    private String descriCatedoria;

    private FotoProdutoInputDTO foto;

}
