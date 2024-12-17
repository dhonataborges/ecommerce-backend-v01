package com.backend.ecommerce.domain.exception.estoqueException;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

public class EstoqueNaoEncontradoException  extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public EstoqueNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstoqueNaoEncontradoException(Long estoqueId) {
        this(String.format("Não existe um produto em estoque com código %d", estoqueId));
    }
}
