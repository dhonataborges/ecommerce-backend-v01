package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.EstoqueNaoEncontradoException;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Serviço responsável por gerenciar as operações de negócio relacionadas ao estoque de produtos.
 * Inclui funcionalidades de cadastro, atualização, exclusão e consulta com validações.
 */
@Service
public class EstoqueService {

    private static final String MSG_PRODUTO_EM_USO =
            "Produto em Estoque do código %d não pode ser removido, pois está em uso.";

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoService produtoService;

    /**
     * Cadastra um novo item no estoque, garantindo que o produto não esteja duplicado.
     *
     * @param estoque Entidade contendo as informações do estoque.
     * @return Estoque salvo.
     */
    @Transactional
    public Estoque salvar(@Valid Estoque estoque) {
        Long produtoId = estoque.getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        String codProduto = buscaCodProd(estoque);

        // Valida se o produto já está cadastrado no estoque
        if (codProduto != null) {
            throw new NegocioException(
                    String.format("Produto do código %s já está cadastrado no estoque!", codProduto)
            );
        }

        calValorTotalProd(estoque);
        estoque.setProduto(produto);

        return estoqueRepository.save(estoque);
    }

    /**
     * Atualiza um item existente no estoque.
     *
     * @param estoque Entidade com os dados atualizados.
     * @return Estoque atualizado.
     */
    @Transactional
    public Estoque atualizar(Estoque estoque) {
        Long produtoId = estoque.getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        calValorTotalProd(estoque);
        estoque.setProduto(produto);

        return estoqueRepository.save(estoque);
    }

    /**
     * Retorna o código do produto já cadastrado no estoque, caso exista.
     *
     * @param estoque Estoque contendo o produto.
     * @return Código do produto se existente, senão null.
     */
    private String buscaCodProd(Estoque estoque) {
        Long produtoId = estoque.getProduto().getId();
        return estoqueRepository.codProd(produtoId);
    }

    /**
     * Exclui um item do estoque pelo ID, tratando possíveis exceções.
     *
     * @param estoqueId ID do item de estoque a ser removido.
     */
    @Transactional
    public void excluir(Long estoqueId) {
        try {
            estoqueRepository.deleteById(estoqueId);
        } catch (EmptyResultDataAccessException e) {
            throw new EstoqueNaoEncontradoException(estoqueId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, estoqueId)
            );
        }
    }

    /**
     * Busca um item de estoque pelo ID, lançando exceção se não encontrado.
     *
     * @param estoqueId ID do estoque.
     * @return Estoque correspondente.
     */
    public Estoque buscarOuFalhar(Long estoqueId) {
        return estoqueRepository.findById(estoqueId)
                .orElseThrow(() -> new EstoqueNaoEncontradoException(estoqueId));
    }

    /**
     * Calcula o valor total do produto em estoque com base na quantidade e valor unitário.
     *
     * @param estoque Objeto de estoque a ser processado.
     */
    private void calValorTotalProd(Estoque estoque) {
        if (estoque.getQuantidade() == null) {
            throw new NullPointerException("Quantidade está null, verifique o motivo.");
        }

        if (estoque.getValorUnitario() == null) {
            throw new NullPointerException("Valor Unitário está null, verifique o motivo.");
        }

        BigDecimal valorTotalProd = BigDecimal.valueOf(estoque.getQuantidade())
                .multiply(estoque.getValorUnitario());

        estoque.setValorTotal(valorTotalProd);
    }
}
