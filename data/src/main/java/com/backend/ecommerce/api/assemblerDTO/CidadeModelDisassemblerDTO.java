package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.CidadeInputDTO;
import com.backend.ecommerce.api.modelDTO.input.EstadoInputDTO;
import com.backend.ecommerce.domain.model.Cidade;
import com.backend.ecommerce.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelDisassemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto do tipo CidadeInputDTO para um objeto do tipo Cidade.
     * Ele utiliza a biblioteca ModelMapper para simplificar a conversão entre os objetos e reduzir a quantidade de código.
     *
     * @param cidadeInputDTO Objeto de entrada do tipo CidadeInputDTO a ser convertido.
     * @return Retorna o objeto Cidade convertido.
     */
    public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO) {
        // Converte o CidadeInputDTO para um objeto Cidade
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }
}
