package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelDisassemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto EstadoInputDTO para um objeto do tipo Estado.
     * Ele utiliza o ModelMapper para reduzir o código e realizar a transformação de dados.
     *
     * @param estadoInputDTO Objeto de entrada do tipo EstadoInputDTO a ser convertido.
     * @return Retorna o objeto Estado convertido.
     */
    public Estado toDomainObject(EstadoInputDTO estadoInputDTO) {

        // Converte o EstadoInputDTO para um objeto do tipo Estado utilizando o ModelMapper
        return modelMapper.map(estadoInputDTO, Estado.class);
    }
}
