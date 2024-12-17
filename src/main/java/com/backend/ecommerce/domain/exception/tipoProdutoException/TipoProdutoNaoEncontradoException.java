package com.backend.ecommerce.domain.exception.tipoProdutoException;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

public class TipoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L;

    public TipoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public TipoProdutoNaoEncontradoException(Long tipoProdutoId) {
        this(String.format("Não existe esse tipo com código %d", tipoProdutoId));
    }

}
