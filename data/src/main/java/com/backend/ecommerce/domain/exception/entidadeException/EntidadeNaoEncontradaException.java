package com.backend.ecommerce.domain.exception.entidadeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.backend.ecommerce.domain.exception.NegocioException;

/**
 * Exceção abstrata lançada quando uma entidade não é encontrada no sistema.
 * Esta classe estende 'NegocioException' e é utilizada como base para outras exceções
 * específicas de entidades não encontradas, como Produto, Cliente, Pedido, entre outras.
 * <p>
 * A anotação '@ResponseStatus' indica que esta exceção, quando lançada, deve retornar
 * um código HTTP 404 (NOT_FOUND) para o cliente, indicando que o recurso não foi encontrado.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND) // Define o código de status HTTP como 404 (Não Encontrado)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem); // Chama o construtor da classe pai (NegocioException)
    }

}
