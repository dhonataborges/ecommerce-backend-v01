package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando uma forma de pagamento específica não é encontrada no sistema.
 * Estende a classe 'EntidadeNaoEncontradaException' para padronizar o tratamento de
 * falhas relacionadas à ausência de registros de forma de pagamento.
 */
public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada de erro.
     *
     * @param mensagem Descrição detalhada do erro ocorrido.
     */
    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera automaticamente uma mensagem padrão informando que a forma de pagamento
     * com o ID fornecido não foi encontrada.
     *
     * @param formaPagamentoId Identificador da forma de pagamento não encontrada.
     */
    public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d.", formaPagamentoId));
    }

}