package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.CidadeModelDTO;
import com.backend.ecommerce.api.modelDTO.EstadoModelDTO;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto do tipo Cidade para um objeto do tipo CidadeModelDTO.
     * Ele utiliza a biblioteca ModelMapper para simplificar a conversão entre os objetos e reduzir a quantidade de código.
     *
     * @param cidade Objeto do tipo Cidade a ser convertido.
     * @return Retorna o objeto CidadeModelDTO convertido.
     */
    public CidadeModelDTO toModel(Cidade cidade) {
        // Converte o objeto Cidade para um objeto CidadeModelDTO
        return modelMapper.map(cidade, CidadeModelDTO.class);
    }

    /**
     * Este método é responsável por converter uma lista de objetos Cidade em uma lista de objetos CidadeModelDTO.
     * Ele usa o método toModel para converter cada objeto individualmente na lista.
     *
     * @param cidades Lista de objetos Cidade a ser convertida.
     * @return Retorna uma lista de objetos CidadeModelDTO convertidos.
     */
    public List<CidadeModelDTO> toCollectionModel(List<Cidade> cidades) {
        // Converte cada cidade da lista para um modelo DTO e retorna a lista de DTOs
        return cidades.stream()
                .map(cidade -> toModel(cidade))  // Para cada cidade na lista, chama o método toModel
                .collect(Collectors.toList());   // Coleta os resultados em uma lista
    }
}