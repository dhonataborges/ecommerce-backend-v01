package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando uma cidade específica não é encontrada no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', proporcionando um tratamento
 * padronizado para erros relacionados à ausência de registros de cidade no sistema.
 */
public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem detalhada sobre o erro ocorrido.
     */
    public CidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que a cidade com o ID fornecido
     * não foi encontrada no sistema.
     *
     * @param estadoId Identificador da cidade não encontrada.
     */
    public CidadeNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de cidade com código %d.", estadoId));
    }

}
