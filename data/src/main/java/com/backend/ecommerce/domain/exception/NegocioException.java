package com.backend.ecommerce.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada para representar falhas relacionadas a regras de negócio.
 * Utilizada quando um erro de validação ou regra de negócio é violado no sistema.
 * A anotação @ResponseStatus permite que a exceção seja automaticamente mapeada para
 * um código de status HTTP 400 (BAD_REQUEST) quando for retornada em um controlador REST.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST) // Mapeia automaticamente para o status HTTP 400 (Bad Request)
public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização da classe

    /**
     * Construtor que permite fornecer uma mensagem personalizada de erro.
     *
     * @param mensagem Descrição detalhada do erro de negócio.
     */
    public NegocioException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor que permite fornecer uma mensagem de erro junto com a causa raiz (exceção original).
     *
     * @param mensagem Descrição do erro de negócio.
     * @param causa    Exceção original que causou esse erro.
     */
    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}