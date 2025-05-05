package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.EstoqueInputDTO;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstoqueModelDisassemblerDTO {

    // Injeta a dependência do ModelMapper, utilizado para mapear objetos entre diferentes tipos.
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método converte um objeto EstoqueInputDTO para um objeto do tipo Estoque (domínio).
     * Ele utiliza a biblioteca ModelMapper para realizar o mapeamento entre os dois tipos de objetos.
     *
     * @param estoqueInputDTO Objeto de entrada com os dados a serem convertidos para o modelo de domínio Estoque.
     * @return Retorna o objeto Estoque convertido a partir do EstoqueInputDTO.
     */
    public Estoque toDomainObject(EstoqueInputDTO estoqueInputDTO) {
        // Utiliza o ModelMapper para converter o EstoqueInputDTO para um objeto Estoque
        return modelMapper.map(estoqueInputDTO, Estoque.class);
    }
}
