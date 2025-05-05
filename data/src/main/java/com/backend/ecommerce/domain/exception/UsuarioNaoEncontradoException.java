package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um usuário não é encontrado no sistema.
 * Estende a classe base 'EntidadeNaoEncontradaException' para seguir o padrão de tratamento
 * de entidades não localizadas no domínio da aplicação.
 */
public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite definir uma mensagem customizada de erro.
     *
     * @param mensagem descrição do erro.
     */
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando o ID do usuário não encontrado.
     *
     * @param userId identificador do usuário buscado.
     */
    public UsuarioNaoEncontradoException(Long userId) {
        this(String.format("Não existe um cadastro de usuário com código %d.", userId));
    }

}

