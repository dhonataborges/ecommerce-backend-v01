package com.backend.ecommerce.api.assemblerDTO;


import com.backend.ecommerce.api.modelDTO.input.PedidoInputDTO;
import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.model.Endereco;
import com.backend.ecommerce.domain.model.Pedido;
import com.backend.ecommerce.domain.model.Usuario;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.backend.ecommerce.domain.repository.EnderecoRepository;
import com.backend.ecommerce.domain.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PedidoModelDisassemblerDTO {

    @Autowired
    private ModelMapper modelMapper; // Utiliza o ModelMapper para mapear objetos entre DTOs e entidades.

    @Autowired
    private EnderecoRepository enderecoRepository; // Repositório responsável por acessar e persistir dados de Endereço.

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório responsável por acessar e persistir dados de Usuário.

    // Esse método converte um PedidoInputDTO em um objeto Pedido (domínio).
    // A conversão inclui validações para garantir que o usuário e o endereço sejam válidos antes de criar o pedido.
    public Pedido toDomainObject(PedidoInputDTO pedidoInputDTO) {

        // Exibe o ID do endereço para fins de depuração, se o endereço não for nulo.
        System.out.printf("Endereço Aqui ==> %d%n", pedidoInputDTO.getEndereco() != null ? pedidoInputDTO.getEndereco().getId() : null);

        // Valida se o usuário foi passado no DTO e se o ID do usuário é válido.
        // Caso contrário, lança uma exceção com uma mensagem detalhada.
        if (pedidoInputDTO.getUsuario() == null || pedidoInputDTO.getUsuario().getId() == null) {
            throw new NegocioException("ERRO: Usuário está null ou sem ID. Pedido precisa de um usuário já cadastrado para ser salvo!");
        }

        // Cria um objeto Usuario e define o ID do usuário a partir do DTO.
        Usuario usuario = new Usuario();
        usuario.setId(pedidoInputDTO.getUsuario().getId());

        // O endereço pode ser opcional. Se fornecido, verifica se o endereço existe no banco de dados.
        Endereco endereco = null;
        if (pedidoInputDTO.getEndereco() != null && pedidoInputDTO.getEndereco().getId() != null) {
            // Tenta recuperar o endereço do banco de dados, se o ID for válido.
            // Se o endereço não for encontrado, o valor será null.
            endereco = enderecoRepository.findById(pedidoInputDTO.getEndereco().getId())
                    .orElse(null);
        }

        // Cria um novo objeto Pedido e atribui os valores extraídos do DTO.
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario); // Atribui o usuário ao pedido.
        pedido.setEndereco(endereco); // Atribui o endereço (pode ser null, conforme verificação acima).
        pedido.setDataPedido(LocalDate.now()); // Define a data do pedido como a data atual.

        // Verifica o status do pedido no DTO. Se não informado, define o status como ABERTO (valor padrão).
        if (pedidoInputDTO.getCodStatus() == null) {
            pedido.setStatus(StatusPedido.ABERTO);
        } else {
            // Se o status for informado, converte o código para o valor do enum StatusPedido correspondente.
            pedido.setStatus(StatusPedido.toEnum(pedidoInputDTO.getCodStatus()));
        }

        // Retorna o objeto Pedido já preenchido com os dados extraídos do DTO.
        return pedido;
    }
}