package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.vendaProdutoException.VendaProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.model.VendaProduto;
import com.backend.ecommerce.domain.repository.VendaProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class VendaProdutoService {
    private static  final String MSG_VENDA_PRODUTO_EM_USO = "Produto a venda do codigo %d não pode ser removido, pois está em uso";

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public VendaProduto salvar(@Valid VendaProduto vendaProduto) {

        /*if (buscaCodProd(produto) != null) {
            throw new DataIntegrityViolationException("Produto já está cadastrado!");
        }*/

        calcValorParcela(vendaProduto);

        Long estoqueId = vendaProduto.getEstoque().getId();
        Estoque estoque = estoqueService.buscarOuFalhar(estoqueId);

        Long produtoId = vendaProduto.getEstoque().getProduto().getId();
        Produto produto = produtoService.buscarOuFalhar(produtoId);

        estoque.setProduto(produto);

        vendaProduto.setEstoque(estoque);

        return vendaProdutoRepository.save(vendaProduto);

    }

    @Transactional
    public void excluir(Long vendaProdutoId) {
        try {
            vendaProdutoRepository.deleteById(vendaProdutoId);
        } catch (EmptyResultDataAccessException e) {
            throw new VendaProdutoNaoEncontradoException(vendaProdutoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_VENDA_PRODUTO_EM_USO, vendaProdutoId));
        }
    }

    public VendaProduto buscarOuFalhar(Long vendaProdutoId) {
        return vendaProdutoRepository.findById(vendaProdutoId).orElseThrow(() -> new VendaProdutoNaoEncontradoException(vendaProdutoId));
    }

    private void calcValorParcela(VendaProduto vendaProduto) {

        DecimalFormat formatResult = new DecimalFormat("0.00");

        BigDecimal result = new BigDecimal(String.valueOf(vendaProduto.getDescontoPorcento())).multiply(vendaProduto.getValorVenda());

        BigDecimal valorPorcento = new BigDecimal(String.valueOf(result)).divide(new BigDecimal("100.00"));

        BigDecimal valorComDesconto = new BigDecimal(String.valueOf(vendaProduto.getValorVenda())).subtract(new BigDecimal(String.valueOf(valorPorcento)));

        BigDecimal valorParcela = new BigDecimal(String.valueOf(valorComDesconto)).divide(new BigDecimal(vendaProduto.getNumParcela().intValue()), RoundingMode.HALF_UP);

        formatResult.format(valorParcela);

        System.out.println("Valor dividido:"+ valorParcela);

        vendaProduto.setValorParcela(valorParcela);
       // vendaProduto.setValorParcela(valorParcela);


    }

}
