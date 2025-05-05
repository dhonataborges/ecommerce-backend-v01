package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.PedidoItemNaoEncontradoException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.model.*;
import com.backend.ecommerce.domain.model.enuns.StatusPedido;
import com.backend.ecommerce.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
/**
 * Serviço responsável por gerenciar as operações dos itens dentro de um pedido.
 * Isso inclui a adição, atualização e exclusão de itens no pedido, além de recalcular os totais do pedido
 * e garantir a integridade dos dados de estoque e valores.
 */
@Service
public class PedidoItemService {

    // Mensagens de erro
    private static final String MSG_PEDIDO_ITEM_EM_USO = "Pedido item do codigo %d não pode ser removido, pois está em uso";
    private static final String MSG_PRODUTO_NULL_OU_ZERO = "Produto em estoque é igual a: %d!";
    private static final String MSG_QUANTIDADE_INSUFICIENTE = "A quantidade solicitada não tem em estoque, só temos %d unidades.";

    // Repositórios necessários para interação com o banco de dados
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CatalogoProdutoRepository catalogoProdutoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    private CatalogoProdutoService catalogoProdutoService;

    /**
     * Atualiza a quantidade de um item no pedido.
     * Calcula o valor total do item de acordo com a nova quantidade e atualiza o pedido com os totais corrigidos.
     *
     * @param id ID do PedidoItem a ser atualizado.
     * @param novaQuantidade Nova quantidade do item.
     * @return O PedidoItem atualizado.
     */
    @Transactional
    public PedidoItem atualizarQuantidade(Long id, int novaQuantidade) {
        // Busca o item no banco de dados
        PedidoItem pedidoItem = pedidoItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PedidoItem não encontrado"));

        // Calcula o valor total com o novo valor de quantidade
        BigDecimal valorComDesconto = pedidoItem.getCatalogo().getValorComDesconto(); // já existente no item
        pedidoItem.setQuantidade(novaQuantidade);
        pedidoItem.setValorTotal(valorComDesconto.multiply(BigDecimal.valueOf(novaQuantidade)));

        // Salva o item atualizado
        pedidoItemRepository.save(pedidoItem);

        // Atualiza os totais do pedido
        Long usuarioId = pedidoItem.getPedido().getUsuario().getId();
        Integer quantidadeItens = pedidoRepository.somarQuantidadeTotalDosItensPorUsuario(usuarioId);
        BigDecimal valorTotalItens = pedidoRepository.somarValorTotalDosItensPorUsuario(usuarioId);
        pedidoItem.getPedido().setQuantidadeItem(quantidadeItens);
        pedidoItem.getPedido().setValorTotal(valorTotalItens);

        return pedidoItemRepository.save(pedidoItem);
    }

    /**
     * Adiciona um item ao pedido. Caso o item já exista no pedido, a quantidade será atualizada.
     * Caso contrário, um novo item será criado e adicionado.
     *
     * @param pedidoItem O item a ser salvo ou atualizado.
     * @return O PedidoItem salvo ou atualizado.
     */
    @Transactional
    public PedidoItem salvar(PedidoItem pedidoItem) {
        Long catalogoId = pedidoItem.getCatalogo().getId();

        // Simula o usuário logado (alterar posteriormente para o ID real do usuário logado)
        Usuario usuario = new Usuario();
        usuario.setId(4L); // TODO: substituir pelo ID real do usuário logado

        // Verifica se já existe um pedido aberto para o usuário
        Optional<Pedido> pedidoOptional = pedidoRepository.findPedidoByUsuarioIdAndStatus(usuario.getId(), StatusPedido.ABERTO);
        Pedido pedido = pedidoOptional.orElseGet(() -> pedidoService.salvar(new Pedido()));

        // Verifica se o item já existe no pedido
        Optional<PedidoItem> itemExistente = pedidoItemRepository.buscarItemPorUsuarioECatalogoEStatus(usuario.getId(), catalogoId, StatusPedido.ABERTO);
        BigDecimal valorUnitario = catalogoProdutoRepository.buscarValorComDesconto(catalogoId);
        PedidoItem item;

        // Caso o item já exista, apenas atualiza a quantidade
        if (itemExistente.isPresent()) {
            item = itemExistente.get();
            int novaQuantidade = item.getQuantidade() + pedidoItem.getQuantidade();
            item.setQuantidade(novaQuantidade);
        } else {
            // Caso contrário, cria um novo item
            item = new PedidoItem();
            item.setPedido(pedido);
            item.setCatalogo(pedidoItem.getCatalogo());
            item.setQuantidade(pedidoItem.getQuantidade());
            item.setValorUnitario(valorUnitario);
        }

        // Atualiza o valor total do item
        BigDecimal valorComDesconto = catalogoProdutoRepository.buscarValorComDesconto(catalogoId);
        item.setValorTotal(valorComDesconto.multiply(BigDecimal.valueOf(item.getQuantidade())));

        // Salva o item no banco
        pedidoItemRepository.save(item);

        // Atualiza os totais do pedido, mas sem salvar novamente
        Integer totalItens = pedidoRepository.somarQuantidadeTotalDosItensPorUsuario(usuario.getId());
        BigDecimal totalValor = pedidoRepository.somarValorTotalDosItensPorUsuario(usuario.getId());

        pedido.setQuantidadeItem(totalItens);
        pedido.setValorTotal(totalValor);

        return item;
    }

    /**
     * Exclui um item de pedido. Caso o item esteja em uso, uma exceção será lançada.
     * Após excluir o item, recalcula os totais do pedido.
     *
     * @param pedidoItemId ID do item a ser excluído.
     */
    @Transactional
    public void excluir(Long pedidoItemId) {
        // Busca o PedidoItem no banco de dados
        PedidoItem pedidoItem = pedidoItemRepository.findById(pedidoItemId)
                .orElseThrow(() -> new PedidoItemNaoEncontradoException(pedidoItemId));

        try {
            // Realiza a exclusão do item
            pedidoItemRepository.delete(pedidoItem);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção se o item estiver em uso
            throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_ITEM_EM_USO, pedidoItemId));
        }

        // Recalcula os totais do pedido após a exclusão do item
        Pedido pedido = pedidoItem.getPedido();
        Long usuarioId = pedido.getUsuario().getId();
        Integer quantidadeItens = pedidoRepository.somarQuantidadeTotalDosItensPorUsuario(usuarioId);
        BigDecimal valorTotalItens = pedidoRepository.somarValorTotalDosItensPorUsuario(usuarioId);

        if (valorTotalItens == null) {
            valorTotalItens = BigDecimal.ZERO;
        }

        // Se não houver mais itens no pedido, o total do pedido será 0
        if (valorTotalItens.compareTo(BigDecimal.ZERO) == 0 && quantidadeItens != null && quantidadeItens.equals(0)) {
            pedido.setQuantidadeItem(0);
            pedido.setValorTotal(BigDecimal.ZERO);
        } else {
            pedido.setQuantidadeItem(quantidadeItens != null ? quantidadeItens : 0);
            pedido.setValorTotal(valorTotalItens);
        }

        // Salva o pedido com os novos totais
        pedidoRepository.save(pedido);
    }

    /**
     * Busca um item de pedido pelo ID. Caso o item não seja encontrado, uma exceção será lançada.
     *
     * @param pedidoItemId ID do item a ser buscado.
     * @return O PedidoItem encontrado.
     */
    public PedidoItem buscarOuFalhar(Long pedidoItemId) {
        return pedidoItemRepository.findById(pedidoItemId)
                .orElseThrow(() -> new PedidoItemNaoEncontradoException(pedidoItemId));
    }

    /**
     * Calcula o valor total de um item de pedido com base no valor unitário e quantidade.
     *
     * @param pedidoItem O PedidoItem a ser calculado.
     * @return O PedidoItem com o valor total atualizado.
     */
    private PedidoItem calValorTotalDoItem(PedidoItem pedidoItem) {
        Long catalogoId = pedidoItem.getCatalogo().getId();
        BigDecimal valorUnitario = catalogoProdutoRepository.buscarValorUnitario(catalogoId);
        BigDecimal quantidade = BigDecimal.valueOf(pedidoItem.getQuantidade());
        BigDecimal valorTotal = valorUnitario.multiply(quantidade);

        pedidoItem.setValorTotal(valorTotal);
        return pedidoItem;
    }
}
