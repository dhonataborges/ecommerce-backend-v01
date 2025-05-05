package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FotoProdutoModelDTO;
import com.backend.ecommerce.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssemblerDTO {

    // Injeta a dependência do ModelMapper, que facilita a conversão entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto do tipo FotoProduto para
     * um objeto do tipo FotoProdutoModelDTO. A conversão é feita utilizando a
     * biblioteca ModelMapper, que facilita e reduz o código necessário para a conversão
     * entre tipos de objetos.
     *
     * @param foto Objeto de domínio do tipo FotoProduto
     * @return Retorna um objeto FotoProdutoModelDTO convertido.
     */
    public FotoProdutoModelDTO toModel(FotoProduto foto) {

        // Usa o ModelMapper para mapear as propriedades do FotoProduto
        // para um novo objeto FotoProdutoModelDTO
        return modelMapper.map(foto, FotoProdutoModelDTO.class);
    }

}