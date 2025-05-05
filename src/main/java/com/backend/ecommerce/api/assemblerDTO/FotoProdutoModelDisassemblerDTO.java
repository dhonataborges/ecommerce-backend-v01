package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.FotoProdutoInputDTO;
import com.backend.ecommerce.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelDisassemblerDTO {

    // Injeta a dependência do ModelMapper, que facilita a conversão entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto do tipo FotoProdutoInputDTO
     * para um objeto do tipo FotoProduto. A conversão é feita utilizando a biblioteca
     * ModelMapper, que facilita e reduz o código necessário para a conversão entre tipos.
     *
     * @param fotoProdutoInputDTO Objeto de entrada do tipo FotoProdutoInputDTO
     * @return Retorna um objeto FotoProduto convertido.
     */
    public FotoProduto toDomainObject(FotoProdutoInputDTO fotoProdutoInputDTO) {

        // Usa o ModelMapper para mapear as propriedades do FotoProdutoInputDTO
        // para um novo objeto FotoProduto
        return modelMapper.map(fotoProdutoInputDTO, FotoProduto.class);

    }
}

