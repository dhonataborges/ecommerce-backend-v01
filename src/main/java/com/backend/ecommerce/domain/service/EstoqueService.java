package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.estoqueException.EstoqueNaoEncontradoException;
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

@Service
public class EstoqueService {
    private static  final String MSG_PRODUTO_EM_USO = "Produto em Estoque do codigo %d não pode ser removido, pois está em uso";

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoService produtoService;

    private Double valorTotalProd;

    @Transactional
    public Estoque salvar(@Valid Estoque estoque) {

        /*if (buscaCodProd(produto) != null) {
            throw new DataIntegrityViolationException("Produto já está cadastrado!");
        }*/

        calValorTotalProd(estoque);


        Long produtoId = estoque.getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        estoque.setProduto(produto);

        return estoqueRepository.save(estoque);
    }

    @Transactional
    public void excluir(Long estoqueId) {
        try {
            estoqueRepository.deleteById(estoqueId);
        } catch (EmptyResultDataAccessException e) {
            throw new EstoqueNaoEncontradoException(estoqueId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, estoqueId));
        }
    }

    @Transactional
    public Estoque buscarOuFalhar(Long estoqueId) {
        return estoqueRepository.findById(estoqueId).orElseThrow(() -> new EstoqueNaoEncontradoException(estoqueId));
    }

    /*
        /*
	* Busca o codigo do produto

    private Produto buscaCodProd(Produto codProd) {
        Produto cod = produtoRepository.findByCodProd(codProd.getCodProd());

        if (cod != null) {
            return cod;
        }
        return null;

    }
    * */

    @Transactional
    private void calValorTotalProd(Estoque estoque) {

        BigDecimal valorTotalProd = new BigDecimal(estoque.getQtdProd()).multiply(BigDecimal.valueOf(estoque.getValorUnid().doubleValue()));

        estoque.setValorTotalProd(valorTotalProd);

    }

}
