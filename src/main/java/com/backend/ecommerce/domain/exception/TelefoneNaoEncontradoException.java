package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um telefone associado a um cliente não é encontrado.
 * Estende a exceção genérica 'EntidadeNaoEncontradaException' para seguir o padrão da aplicação.
 */
public class TelefoneNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Versão da classe para fins de serialização

    /**
     * Construtor que permite especificar uma mensagem personalizada de erro.
     *
     * @param mensagem descrição detalhada da falha.
     */
    public TelefoneNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera automaticamente uma mensagem padrão com o ID do cliente.
     *
     * @param clienteId identificador do cliente cujo telefone não foi encontrado.
     */
    public TelefoneNaoEncontradoException(Long clienteId) {
        this(String.format("Não existe um cadastro de cliente com código %d.", clienteId));
    }

}
