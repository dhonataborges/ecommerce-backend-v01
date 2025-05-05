package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.NegocioException;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.CatalogoProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.model.CatalogoProduto;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.repository.CatalogoProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Serviço responsável pela regra de negócio relacionada ao catálogo de produtos.
 * Inclui validação de dados, persistência e atualização de informações de catálogo.
 */
@Service
public class CatalogoProdutoService {

    private static final String MSG_CATALOGO_PRODUTO_EM_USO = "Catálogo de produto com código %d não pode ser removido, pois está em uso.";

    @Autowired
    private CatalogoProdutoRepository catalogoProdutoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;

    /**
     * Cadastra um novo produto no catálogo, garantindo que não haja duplicidade e que as dependências estejam corretamente associadas.
     *
     * @param catalogoProduto Instância a ser salva.
     * @return Catálogo de produto persistido.
     * @throws NegocioException Se o produto já estiver vinculado a um ponto de venda.
     */
    @Transactional
    public CatalogoProduto salvar(@Valid CatalogoProduto catalogoProduto) {
        String codProduto = buscaCodProd(catalogoProduto);

        if (codProduto != null) {
            throw new NegocioException(
                    String.format("Produto do código %s já está para no ponto de venda!", codProduto)
            );
        }

        calcValorParcela(catalogoProduto);

        Long estoqueId = catalogoProduto.getEstoque().getId();
        Estoque estoque = estoqueService.buscarOuFalhar(estoqueId);

        Long produtoId = catalogoProduto.getEstoque().getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        estoque.setProduto(produto);
        catalogoProduto.setEstoque(estoque);

        return catalogoProdutoRepository.save(catalogoProduto);
    }

    /**
     * Atualiza um catálogo de produto existente, recalculando valores e atualizando referências.
     *
     * @param catalogoProduto Instância com os dados atualizados.
     * @return Catálogo atualizado.
     */
    @Transactional
    public CatalogoProduto atualizar(@Valid CatalogoProduto catalogoProduto) {
        calcValorParcela(catalogoProduto);

        Long estoqueId = catalogoProduto.getEstoque().getId();
        Estoque estoque = estoqueService.buscarOuFalhar(estoqueId);

        Long produtoId = catalogoProduto.getEstoque().getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        estoque.setProduto(produto);
        catalogoProduto.setEstoque(estoque);

        return catalogoProdutoRepository.save(catalogoProduto);
    }

    /**
     * Exclui um item do catálogo pelo ID, tratando as exceções de integridade e ausência.
     *
     * @param catalogoId Identificador do catálogo.
     * @throws CatalogoProdutoNaoEncontradoException Se o catálogo não existir.
     * @throws EntidadeEmUsoException Se o catálogo estiver vinculado a outra entidade.
     */
    @Transactional
    public void excluir(Long catalogoId) {
        try {
            catalogoProdutoRepository.deleteById(catalogoId);
        } catch (EmptyResultDataAccessException e) {
            throw new CatalogoProdutoNaoEncontradoException(catalogoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CATALOGO_PRODUTO_EM_USO, catalogoId));
        }
    }

    /**
     * Busca um item do catálogo pelo ID ou lança uma exceção se não encontrado.
     *
     * @param catalogoId Identificador do catálogo.
     * @return Instância do catálogo encontrada.
     */
    public CatalogoProduto buscarOuFalhar(Long catalogoId) {
        return catalogoProdutoRepository.findById(catalogoId)
                .orElseThrow(() -> new CatalogoProdutoNaoEncontradoException(catalogoId));
    }

    /**
     * Verifica se o produto já está vinculado a um catálogo.
     *
     * @param catalogoProduto Objeto a ser verificado.
     * @return Código do produto, caso encontrado.
     */
    private String buscaCodProd(CatalogoProduto catalogoProduto) {
        Long produtoId = catalogoProduto.getEstoque().getProduto().getId();
        return catalogoProdutoRepository.codProd(produtoId);
    }

    /**
     * Realiza o cálculo do valor com desconto e das parcelas, com base nos dados informados.
     *
     * @param catalogoProduto Instância com dados de entrada e saída do cálculo.
     */
    private void calcValorParcela(CatalogoProduto catalogoProduto) {
        BigDecimal desconto = Optional.ofNullable(catalogoProduto.getDescontoPorcento()).orElse(BigDecimal.ZERO);
        Integer numeroParcela = Optional.ofNullable(catalogoProduto.getNumParcela()).orElse(0);

        BigDecimal valorUnitario = estoqueRepository.valorUnitario(catalogoProduto.getEstoque().getId());

        BigDecimal valorDoDesconto = valorUnitario.multiply(desconto)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal valorComDesconto = valorUnitario.subtract(valorDoDesconto);
        catalogoProduto.setValorComDesconto(valorComDesconto);

        if (numeroParcela <= 0) {
            catalogoProduto.setValorParcela(BigDecimal.ZERO);
        } else {
            BigDecimal valorParcela = valorComDesconto
                    .divide(BigDecimal.valueOf(numeroParcela), 2, RoundingMode.HALF_UP);
            catalogoProduto.setValorParcela(valorParcela);
        }
    }

    // Método legado comentado, mantido por histórico
    /*
    public List<Pedido> listarPedidosPorVendaProduto(Long vendaProdutoId) {
        VendaProduto vendaProduto = buscarOuFalhar(vendaProdutoId);
        return vendaProduto.getPedidoClientes();
    }
    */
}
