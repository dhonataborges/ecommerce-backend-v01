package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.PedidoNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.EstoqueNaoEncontradoException;
import com.backend.ecommerce.domain.model.*;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.backend.ecommerce.domain.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão de pedidos. As funcionalidades incluem a criação de novos pedidos,
 * a atualização de pedidos com endereços, a exclusão de pedidos, e a busca de pedidos para um cliente.
 */
@Service
public class PedidoService {

    // Mensagens de erro
    private static final String MSG_PEDIDO_EM_USO = "Pedido do codigo %d não pode ser removido, pois está em uso";
    private static final String MSG_PRODUTO_NULL_OU_ZERO = "Produto em estoque é igual a: %d!";
    private static final String MSG_QUANTIDADE_INSUFICENTE = "A quantidade solicitada não tem em estoque, só temos %d unidades.";

    // Repositórios necessários para interação com o banco de dados
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    /**
     * Busca todos os pedidos feitos por um cliente específico.
     *
     * @param clienteId ID do cliente para buscar os pedidos.
     * @return Lista de pedidos do cliente.
     */
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByUsuarioId(clienteId);
    }

    /**
     * Salva um novo pedido para o usuário logado. Caso já exista um pedido em aberto para o usuário,
     * retorna o pedido existente sem realizar alterações.
     *
     * @param pedidoInput O pedido a ser salvo.
     * @return O pedido salvo (ou o existente).
     */
    @Transactional
    public Pedido salvar(@Valid Pedido pedidoInput) {
        // Simula o usuário logado
        Usuario usuario = new Usuario();
        usuario.setId(4L);  // TODO: Substituir pelo ID real do usuário logado

        // Busca completa do usuário
        Usuario usuarioCompleto = usuarioRepository.buscarUsuarioCompleto(usuario.getId());

        // Verifica se já existe um pedido em aberto para o usuário
        Optional<Pedido> pedidoExistente = pedidoRepository.findPedidoByUsuarioIdAndStatus(usuarioCompleto.getId(), StatusPedido.ABERTO);

        if (pedidoExistente.isPresent()) {
            return pedidoExistente.get(); // Nenhuma alteração é necessária, apenas retorna
        }

        // Cria um novo pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuarioCompleto);
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.ABERTO);

        return pedidoRepository.save(pedido);
    }

    /**
     * Atualiza o endereço de um pedido em aberto para o usuário logado.
     *
     * @param pedidoInput Contém as novas informações de endereço.
     * @return O pedido com o novo endereço.
     */
    @Transactional
    public Pedido atualizarPedidoComEndereco(@Valid Pedido pedidoInput) {
        // Simula o ID do usuário logado (depois você pode substituir pelo valor real)
        Long usuarioId = 4L; // TODO: substituir pelo ID do usuário autenticado

        // Busca o pedido em aberto para o usuário
        Optional<Pedido> pedidoOptional = pedidoRepository.findPedidoByUsuarioIdAndStatus(usuarioId, StatusPedido.ABERTO);

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setEndereco(pedidoInput.getEndereco());
            return pedidoRepository.save(pedido);
        } else {
            // Se não encontrar o pedido, pode lançar uma exceção ou apenas retornar null
            throw new NegocioException("Pedido em aberto não encontrado para o usuário.");
        }
    }

    /**
     * Exclui um pedido. Caso o pedido esteja em uso, uma exceção será lançada.
     *
     * @param pedidoId ID do pedido a ser excluído.
     */
    @Transactional
    public void excluir(Long pedidoId) {
        try {
            Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);

            if (pedidoOptional.isPresent()) {
                Pedido pedido = pedidoOptional.get();
                Long usuarioId = pedido.getUsuario().getId();

                // Exclui o pedido
                pedidoRepository.deleteById(pedidoId);

                // Atualiza o subtotal do cliente (comentado por enquanto)
                //   atualizarSubTotalPedidoPorCliente(pedido);
            } else {
                throw new EstoqueNaoEncontradoException(pedidoId);
            }

        } catch (EmptyResultDataAccessException e) {
            throw new EstoqueNaoEncontradoException(pedidoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_EM_USO, pedidoId));
        }
    }

    /**
     * Busca um pedido pelo ID. Caso o pedido não seja encontrado, uma exceção será lançada.
     *
     * @param pedidoId ID do pedido a ser buscado.
     * @return O Pedido encontrado.
     */
    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

}