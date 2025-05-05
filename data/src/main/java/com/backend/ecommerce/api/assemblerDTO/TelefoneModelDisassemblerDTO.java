package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.EstoqueInputDTO;
import com.backend.ecommerce.api.modelDTO.input.TelefoneInputDTO;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Telefone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelefoneModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper;

    // Esse método converte um DTO de entrada (TelefoneInputDTO) em uma entidade de domínio (Telefone).
    // Aqui a gente usa o ModelMapper, que já cuida do mapeamento automático dos campos com nomes iguais.
    // Isso evita ter que ficar fazendo set/get na mão pra cada propriedade.

    public Telefone toDomainObject(TelefoneInputDTO telefoneInputDTO) {
        return modelMapper.map(telefoneInputDTO, Telefone.class);
    }
}