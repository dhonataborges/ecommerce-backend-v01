package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto Estado para um objeto do tipo EstadoModelDTO.
     * Ele utiliza o ModelMapper para reduzir o código e realizar a transformação de dados.
     *
     * @param estado Objeto de entrada do tipo Estado a ser convertido.
     * @return Retorna o objeto EstadoModelDTO convertido.
     */
    public EstadoModelDTO toModel(Estado estado) {
        // Converte o Estado para um objeto do tipo EstadoModelDTO utilizando o ModelMapper
        return modelMapper.map(estado, EstadoModelDTO.class);
    }

    /**
     * Este método é responsável por converter uma lista de objetos Estado para uma lista de objetos do tipo EstadoModelDTO.
     *
     * @param estados Lista de objetos Estado a serem convertidos.
     * @return Retorna uma lista de objetos EstadoModelDTO convertidos.
     */
    public List<EstadoModelDTO> toCollectionModel(List<Estado> estados) {
        // Converte a lista de Estados para uma lista de EstadoModelDTO utilizando stream e o método toModel
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
}
