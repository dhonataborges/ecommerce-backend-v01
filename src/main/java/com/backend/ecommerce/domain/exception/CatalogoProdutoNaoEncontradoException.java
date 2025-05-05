package com.backend.ecommerce.domain.exception;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;

/**
 * Exceção lançada quando não é encontrado um catálogo de produto com o código especificado.
 * Esta exceção estende a classe 'EntidadeNaoEncontradaException' e é usada para indicar que
 * uma operação não pôde ser realizada porque o catálogo de produto não foi encontrado.
 */
public class CatalogoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public CatalogoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem); // Chama o construtor da classe pai (EntidadeNaoEncontradaException)
    }

    /**
     * Construtor que cria uma exceção com base no ID do catálogo de produto não encontrado.
     *
     * @param catalogoId ID do catálogo de produto que não foi encontrado.
     */
    public CatalogoProdutoNaoEncontradoException(Long catalogoId) {
        // Chama o construtor da classe pai com uma mensagem formatada
        this(String.format("Não existe esse tipo com código %d", catalogoId));
    }
}
