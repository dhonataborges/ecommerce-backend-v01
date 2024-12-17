package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoModelDTO toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModelDTO.class);
    }

}
