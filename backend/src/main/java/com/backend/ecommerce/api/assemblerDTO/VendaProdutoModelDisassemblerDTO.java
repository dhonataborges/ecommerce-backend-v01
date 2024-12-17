package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.VendaProdutoInputDTO;
import com.backend.ecommerce.domain.model.VendaProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendaProdutoModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    //Esse metódo é responsavel por pegar uma objeto ProdutoInputDTO e convertelo para um objeto tipo Produto.
    //Aqui está sendo usado uma biblioteca por nome modelMapper ela ajuda a reduzir o tamanho do codigo.

    public VendaProduto toDomainObject(VendaProdutoInputDTO vendaProdutoInputDTO) {

        return modelMapper.map(vendaProdutoInputDTO, VendaProduto.class);
    }


}
