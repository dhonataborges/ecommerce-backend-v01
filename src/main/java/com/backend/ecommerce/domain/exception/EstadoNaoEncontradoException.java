package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um estado específico não é encontrado no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', que é usada para padronizar
 * o tratamento de erros relacionados à ausência de registros de entidades no sistema.
 */
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem que descreve a razão do erro.
     */
    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que o estado com o ID fornecido
     * não foi encontrado no sistema.
     *
     * @param estadoId Identificador do estado não encontrado.
     */
    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de estado com código %d.", estadoId));
    }

}