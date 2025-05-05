package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando a foto de um produto não é encontrada no sistema.
 * Estende 'EntidadeNaoEncontradaException' para seguir o padrão de tratamento de
 * erros de entidades ausentes no sistema.
 */
public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada de erro.
     *
     * @param mensagem Descrição detalhada sobre o erro.
     */
    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera automaticamente uma mensagem informando que a foto de um
     * produto com o ID fornecido não foi encontrada.
     *
     * @param produtoId Identificador do produto cuja foto não foi localizada.
     */
    public FotoProdutoNaoEncontradaException(Long produtoId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d.", produtoId));
    }

}

