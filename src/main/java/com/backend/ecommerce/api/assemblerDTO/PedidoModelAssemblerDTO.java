package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.*;
import com.backend.ecommerce.domain.model.*;
import com.backend.ecommerce.domain.model.enuns.Permissoes;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class PedidoModelAssemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Usado para mapear entre objetos de diferentes camadas (DTOs e entidades).

    @Autowired
    private EnderecoRepository enderecoRepository; // Repositório responsável por acessar e persistir dados de Endereço.

    @Autowired
    private EnderecoModelAssemblerDTO enderecoModelAssemblerDTO; // Responsável por converter objetos de Endereço em DTOs.

    @Autowired
    private EstoqueDoPedidoModelAssemblerDTO estoqueDoPedidoModelAssemblerDTO; // Responsável por converter dados de estoque do pedido em DTOs.

    @Autowired
    PedidoItemModelAssemblerDTO pedidoItemModelAssemblerDTO; // Responsável por converter PedidoItem para DTO.

    // Este método converte um objeto Pedido em um DTO PedidoModelDTO.
    public PedidoModelDTO toModel(Pedido pedido) {
        // Cria um DTO para o usuário do pedido
        UsuarioModelDTO usuarioDTO = new UsuarioModelDTO();

        System.out.println("Endereco do pedido: " + pedido.getEndereco());

        // Verifica se o usuário está presente e preenche os dados necessários no DTO.
        if (pedido.getUsuario() != null) {
            // Se o usuário não tiver permissões definidas, define um valor padrão para permissões
            if (pedido.getUsuario().getPermissoes() == null) {
                usuarioDTO.setCodPermicoes(Permissoes.CLIENTE.getCodigo());
                usuarioDTO.setDescriPermicoes(Permissoes.CLIENTE.getDescricao());
            } else {
                usuarioDTO.setCodPermicoes(pedido.getUsuario().getPermissoes().getCodigo());
                usuarioDTO.setDescriPermicoes(pedido.getUsuario().getPermissoes().getDescricao());
            }
            // Preenche outros dados do usuário
            usuarioDTO.setId(pedido.getUsuario().getId());
            usuarioDTO.setNome(pedido.getUsuario().getNome());
            usuarioDTO.setSobrenome(pedido.getUsuario().getSobrenome());
            usuarioDTO.setEmail(pedido.getUsuario().getEmail());
        } else {
            throw new IllegalArgumentException("ERRO: Usuário retornado do banco de dados está null.");
        }

        // Cria DTO para estado e cidade a partir do endereço
        EstadoModelDTO estadoDTO = new EstadoModelDTO();
        CidadeModelDTO cidadeDTO = new CidadeModelDTO();

        if (pedido.getEndereco() != null) {
            // Preenche os dados de estado e cidade no DTO
            estadoDTO.setId(pedido.getEndereco().getCidade().getEstado().getId());
            estadoDTO.setNome(pedido.getEndereco().getCidade().getEstado().getNome());
            cidadeDTO.setId(pedido.getEndereco().getCidade().getId());
            cidadeDTO.setNome(pedido.getEndereco().getCidade().getNome());
            cidadeDTO.setEstado(estadoDTO);
        }

        // Cria DTO para Endereco a partir dos dados do pedido
        EnderecoModelDTO enderecoDTO = new EnderecoModelDTO();
        if (pedido.getEndereco() != null) {
            enderecoDTO.setId(pedido.getEndereco().getId());
            enderecoDTO.setUsuario(usuarioDTO);
            enderecoDTO.setNomeDono(pedido.getEndereco().getNomeDono());
            enderecoDTO.setCep(pedido.getEndereco().getCep());
            enderecoDTO.setBairro(pedido.getEndereco().getBairro());
            enderecoDTO.setRua(pedido.getEndereco().getRua());
            enderecoDTO.setNumero(pedido.getEndereco().getNumero());
            enderecoDTO.setComplemento(pedido.getEndereco().getComplemento());
            enderecoDTO.setCidade(cidadeDTO);
        } else {
            // Se não houver endereço, mantém o DTO de Endereco com o valor padrão
            enderecoDTO.setId(enderecoDTO.getId());
        }

        // Cria o DTO de Pedido e preenche os dados
        PedidoModelDTO pedidoDTO = new PedidoModelDTO();

        // Define o status do pedido no DTO
        if (pedido.getStatus() == null) {
            pedidoDTO.setCodStatus(StatusPedido.ABERTO.getCodStatus());
            pedidoDTO.setDescriStatus(StatusPedido.ABERTO.getDescricao());
        } else {
            pedidoDTO.setCodStatus(pedido.getStatus().getCodStatus());
            pedidoDTO.setDescriStatus(pedido.getStatus().getDescricao());
        }

        // Preenche os outros dados do pedido
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setUsuario(usuarioDTO);
        pedidoDTO.setEndereco(enderecoDTO);  // Mesmo que o Endereco seja null, preenche o campo
        pedidoDTO.setDataPedido(LocalDate.now());  // Define a data do pedido como a data atual
        pedidoDTO.setQuantidadeItem(pedido.getQuantidadeItem());
        pedidoDTO.setValorTotal(pedido.getValorTotal());

        // Preenche os itens do pedido, convertendo cada PedidoItem para seu DTO correspondente
        pedidoDTO.setPedidoItens(
                pedido.getPedidoItens().stream()
                        .map(pedidoItem -> pedidoItemModelAssemblerDTO.toModel(pedidoItem))
                        .collect(Collectors.toList())
        );

        return pedidoDTO;  // Retorna o DTO do Pedido
    }

    // Método auxiliar para converter uma lista de pedidos para uma lista de DTOs de pedidos
    public List<PedidoModelDTO> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))  // Converte cada pedido da lista para o seu DTO
                .collect(Collectors.toList());  // Coleta os DTOs em uma lista
    }

}