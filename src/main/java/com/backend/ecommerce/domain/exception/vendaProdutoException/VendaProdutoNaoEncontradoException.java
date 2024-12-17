package com.backend.ecommerce.domain.exception.vendaProdutoException;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

public class VendaProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public VendaProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public VendaProdutoNaoEncontradoException(Long vendaProdutoId) {
        this(String.format("Não existe esse tipo com código %d", vendaProdutoId));
    }
}
