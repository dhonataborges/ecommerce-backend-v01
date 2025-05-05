package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um cliente específico não é encontrado no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', garantindo que a
 * lógica de tratamento de erro de entidades ausentes seja consistente em todo o sistema.
 */
public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem que descreve o erro ocorrido.
     */
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão indicando que o cliente com o ID fornecido
     * não foi encontrado no sistema.
     *
     * @param clienteId Identificador do cliente não encontrado.
     */
    public ClienteNaoEncontradoException(Long clienteId) {
        this(String.format("Não existe um cadastro de cliente com código %d.", clienteId));
    }

}

