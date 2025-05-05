package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um pagamento específico não é encontrado no sistema.
 * Esta exceção estende a classe 'EntidadeNaoEncontradaException', que serve como base
 * para o tratamento de falhas relacionadas à ausência de entidades.
 */
public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Versão da classe para controle de serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada explicando o erro.
     *
     * @param mensagem mensagem detalhando a razão da exceção.
     */
    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera automaticamente uma mensagem informando que o pagamento com o
     * ID fornecido não foi encontrado.
     *
     * @param pagamentoId identificador do pagamento não localizado.
     */
    public PagamentoNaoEncontradoException(Long pagamentoId) {
        this(String.format("Não existe um cadastro de pagamento com código %d.", pagamentoId));
    }

}

