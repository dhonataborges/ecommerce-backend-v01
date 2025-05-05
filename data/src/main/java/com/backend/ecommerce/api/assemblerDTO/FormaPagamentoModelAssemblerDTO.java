package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.*;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.FormaPagamento;
import com.backend.ecommerce.domain.model.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssemblerDTO {

    // Injeta a dependência do ModelMapper, utilizado para mapear objetos entre diferentes tipos.
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Este método converte um objeto FormaPagamento para um objeto de modelo do tipo FormaPagamentoModelDTO.
     * Ele utiliza o ModelMapper para realizar o mapeamento entre os dois tipos de objeto.
     *
     * @param formaPagamento Objeto de domínio do pagamento, contendo os dados a serem convertidos.
     * @return Retorna o objeto FormaPagamentoModelDTO com os dados mapeados do FormaPagamento.
     */
    public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
        // O ModelMapper mapeia as propriedades do FormaPagamento para o objeto FormaPagamentoModelDTO
        return modelMapper.map(formaPagamento, FormaPagamentoModelDTO.class);
    }

    /**
     * Este método converte uma lista de objetos FormaPagamento para uma lista de objetos FormaPagamentoModelDTO.
     * Ele utiliza o método toModel para mapear cada objeto individualmente.
     *
     * @param formasPagamento Lista de objetos de domínio FormaPagamento a serem convertidos.
     * @return Retorna uma lista de objetos FormaPagamentoModelDTO com os dados mapeados dos FormaPagamento.
     */
    public List<FormaPagamentoModelDTO> toCollectionModel(List<FormaPagamento> formasPagamento) {
        // Para cada FormaPagamento na lista, o método toModel é chamado para convertê-lo para FormaPagamentoModelDTO
        return formasPagamento.stream()
                .map(this::toModel) // Chama o método toModel para cada item da lista
                .collect(Collectors.toList()); // Coleta os resultados em uma nova lista
    }
}
