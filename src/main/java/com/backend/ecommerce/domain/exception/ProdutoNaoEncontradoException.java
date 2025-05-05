package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um produto específico não é encontrado no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', proporcionando uma maneira
 * padronizada de tratar erros de ausência de registros de produtos no sistema.
 */
public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que o produto com o ID fornecido
     * não foi encontrado no sistema.
     *
     * @param produtoId Identificador do produto não encontrado.
     */
    public ProdutoNaoEncontradoException(Long produtoId) {
        this(String.format("Não existe um cadastro de produto com código %d", produtoId));
    }

}
