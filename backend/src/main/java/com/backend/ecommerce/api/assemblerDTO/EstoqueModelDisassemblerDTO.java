package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.EstoqueInputDTO;
import com.backend.ecommerce.domain.model.Estoque;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstoqueModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    //Esse metódo é responsavel por pegar uma objeto ProdutoInputDTO e convertelo para um objeto tipo Produto.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public Estoque toDomainObject(EstoqueInputDTO estoqueInputDTO) {

        return modelMapper.map(estoqueInputDTO, Estoque.class);
    }


}
