package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.EnderecoInputDTO;
import com.backend.ecommerce.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoModelDisassemblerDTO {

    // Injeção da dependência do ModelMapper para realizar as conversões entre objetos
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método é responsável por converter um objeto EnderecoInputDTO em um objeto do tipo Endereco.
     * Ele utiliza a biblioteca ModelMapper para simplificar a conversão entre os objetos e reduzir a quantidade de código.
     *
     * @param enderecoInputDTO Objeto de entrada do tipo EnderecoInputDTO a ser convertido.
     * @return Retorna o objeto Endereco convertido.
     */
    public Endereco toDomainObject(EnderecoInputDTO enderecoInputDTO) {
        // Converte o EnderecoInputDTO para o objeto Endereco utilizando o ModelMapper
        return modelMapper.map(enderecoInputDTO, Endereco.class);
    }
}