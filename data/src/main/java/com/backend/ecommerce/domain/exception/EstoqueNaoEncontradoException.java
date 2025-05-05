package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um produto específico não é encontrado no estoque.
 * Esta exceção estende 'EntidadeNaoEncontradaException', permitindo um tratamento de erro
 * consistente para situações onde não é possível localizar um produto no estoque.
 */
public class EstoqueNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public EstoqueNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que o produto com o ID fornecido
     * não está disponível no estoque.
     *
     * @param estoqueId Identificador do produto não encontrado no estoque.
     */
    public EstoqueNaoEncontradoException(Long estoqueId) {
        this(String.format("Não existe um produto em estoque com código %d", estoqueId));
    }

}

