package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um endereço específico não é encontrado no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', padronizando o tratamento de
 * erros relacionados à ausência de registros de endereço no sistema.
 */
public class EnderecoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem que descreve a razão do erro.
     */
    public EnderecoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que o endereço com o ID fornecido
     * não foi encontrado no sistema.
     *
     * @param estadoId Identificador do endereço não encontrado.
     */
    public EnderecoNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de endereco com código %d.", estadoId));
    }

}
