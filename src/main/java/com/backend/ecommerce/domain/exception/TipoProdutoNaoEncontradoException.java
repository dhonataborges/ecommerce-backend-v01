package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando um tipo de produto específico não é encontrado no sistema.
 * Esta exceção estende 'EntidadeNaoEncontradaException', garantindo que o tratamento de erro
 * seja padronizado para tipos de produto ausentes no sistema.
 */
public class TipoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public TipoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que gera uma mensagem padrão informando que o tipo de produto com o ID fornecido
     * não foi encontrado no sistema.
     *
     * @param tipoProdutoId Identificador do tipo de produto não encontrado.
     */
    public TipoProdutoNaoEncontradoException(Long tipoProdutoId) {
        this(String.format("Não existe esse tipo com código %d", tipoProdutoId));
    }

}

