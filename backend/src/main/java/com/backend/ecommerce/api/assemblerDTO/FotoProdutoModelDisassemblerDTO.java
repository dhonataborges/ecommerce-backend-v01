package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.FotoProdutoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.ProdutoInputDTO;
import com.backend.ecommerce.domain.model.Categoria;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    //Esse metódo é responsavel por pegar uma objeto ProdutoInputDTO e convertelo para um objeto tipo Produto.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public FotoProduto toDomainObject(FotoProdutoInputDTO fotoProdutoInputDTO) {

        return modelMapper.map(fotoProdutoInputDTO, FotoProduto.class);

      /*FotoProduto foto = new FotoProduto();

        return modelMapper.map(produtoInput, Produto.class);*/
    }


}
