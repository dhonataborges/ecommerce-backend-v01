package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.CatalogoProdutoInputDTO;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogoProdutoModelDisassemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto do tipo CatalogoProdutoInputDTO para um objeto do tipo CatalogoProduto.
     * Ele utiliza a biblioteca ModelMapper para simplificar a conversão entre os objetos e reduzir a quantidade de código.
     *
     * @param pontoDeVendaInputDTO Objeto do tipo CatalogoProdutoInputDTO a ser convertido.
     * @return Retorna o objeto CatalogoProduto convertido.
     */
    public CatalogoProduto toDomainObject(CatalogoProdutoInputDTO pontoDeVendaInputDTO) {
        // Converte o objeto CatalogoProdutoInputDTO para um objeto CatalogoProduto
        return modelMapper.map(pontoDeVendaInputDTO, CatalogoProduto.class);
    }
}