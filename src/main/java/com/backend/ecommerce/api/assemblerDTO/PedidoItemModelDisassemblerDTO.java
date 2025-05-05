package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.PedidoInputDTO;
import com.backend.ecommerce.api.modelDTO.input.PedidoItemInputDTO;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.model.PedidoItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoItemModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Instância do ModelMapper, usado para mapear objetos entre diferentes camadas.

    // Este método converte um PedidoItemInputDTO para um objeto PedidoItem.
    public PedidoItem toDomainObject(PedidoItemInputDTO pedidoItemInputDTO) {
        // Usa o ModelMapper para mapear automaticamente os campos do DTO para a entidade PedidoItem.
        return modelMapper.map(pedidoItemInputDTO, PedidoItem.class);
    }

}