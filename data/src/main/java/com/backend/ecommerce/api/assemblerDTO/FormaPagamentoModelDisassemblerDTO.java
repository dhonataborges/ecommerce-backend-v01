package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FormaPagamentoModelDTO;
import com.backend.ecommerce.api.modelDTO.input.FormaPagamentoInputDTO;
import com.backend.ecommerce.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelDisassemblerDTO {

    // Injeta a dependência do ModelMapper, utilizado para mapear objetos entre diferentes tipos.
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método converte um objeto FormaPagamentoInputDTO para um objeto de domínio do tipo FormaPagamento.
     * Ele utiliza o ModelMapper para realizar o mapeamento entre os dois tipos de objeto.
     *
     * @param formaPagamentoInputDTO Objeto de entrada, contendo os dados do pagamento a serem convertidos.
     * @return Retorna o objeto FormaPagamento com os dados mapeados do FormaPagamentoInputDTO.
     */
    public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO) {

        // O ModelMapper mapeia as propriedades do FormaPagamentoInputDTO para o objeto FormaPagamento
        return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
    }
}