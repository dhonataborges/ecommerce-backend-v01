package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um pedido não é encontrado no sistema.
 * Segue o padrão de exceções do domínio ao estender 'EntidadeNaoEncontradaException',
 * permitindo tratamento centralizado e semântico para falhas desse tipo.
 */
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // ID da versão para controle de serialização

    /**
     * Construtor que permite especificar uma mensagem customizada para a exceção.
     *
     * @param mensagem descrição do erro.
     */
    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando o código do pedido não localizado.
     *
     * @param pedidoItemId identificador do pedido.
     */
    public PedidoNaoEncontradoException(Long pedidoItemId) {
        this(String.format("Não existe um cadastro de pedido com código %d.", pedidoItemId));
    }

}

