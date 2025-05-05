package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.TelefoneModelDTO;
import com.backend.ecommerce.domain.model.Telefone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelefoneModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    // Esse método é responsável por converter uma entidade Telefone em um DTO TelefoneModelDTO.
    // O ModelMapper faz o mapeamento automático das propriedades, evitando a necessidade de copiar campo a campo manualmente.
    public TelefoneModelDTO toModel(Telefone telefone) {
        return modelMapper.map(telefone, TelefoneModelDTO.class);
    }

    // Esse método é responsável por converter uma lista de objetos Telefone para uma lista de DTOs TelefoneModelDTO.
    // Usamos o método toModel em cada item da lista para converter todos os telefones.
    public List<TelefoneModelDTO> toCollectionModel(List<Telefone> telefones) {
        return telefones.stream()
                .map(telefone -> toModel(telefone)) // Converte cada Telefone para TelefoneModelDTO
                .collect(Collectors.toList()); // Coleta os DTOs em uma lista
    }
}

