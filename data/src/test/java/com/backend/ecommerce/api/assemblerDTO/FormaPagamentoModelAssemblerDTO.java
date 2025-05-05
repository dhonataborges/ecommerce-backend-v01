package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.FormaPagamentoModelDTO;
import com.backend.ecommerce.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssemblerDTO {
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModelDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModelDTO.class);
    }

    public List<FormaPagamentoModelDTO> toCollectionModel(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
