package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um item de pedido específico não é encontrado no sistema.
 * Estende 'EntidadeNaoEncontradaException' para padronizar o tratamento de erros
 * relacionados à ausência de entidades.
 */
public class PedidoItemNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite informar uma mensagem personalizada de erro.
     *
     * @param mensagem descrição detalhada do erro ocorrido.
     */
    public PedidoItemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera automaticamente uma mensagem padrão com o ID do item de pedido.
     *
     * @param pedidoItemId identificador do item de pedido não encontrado.
     */
    public PedidoItemNaoEncontradoException(Long pedidoItemId) {
        this(String.format("Não existe um cadastro de pedido item com código %d.", pedidoItemId));
    }

}