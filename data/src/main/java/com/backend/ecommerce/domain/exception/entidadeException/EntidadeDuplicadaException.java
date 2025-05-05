package com.backend.ecommerce.domain.exception.entidadeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.backend.ecommerce.domain.exception.NegocioException;

/**
 * Exceção lançada quando uma tentativa de inserir ou atualizar uma entidade resulta
 * em um conflito devido à duplicação de dados no sistema.
 * Esta exceção estende 'NegocioException' e é usada para indicar que uma operação
 * não pode ser realizada porque a entidade já existe, causando um conflito.
 * <p>
 * A anotação '@ResponseStatus' define que, quando essa exceção for lançada, o código de
 * status HTTP retornado será 409 (CONFLICT), indicando que a operação não pode ser realizada
 * devido a uma duplicação de dados.
 */
@ResponseStatus(value = HttpStatus.CONFLICT) // Define o código de status HTTP como 409 (Conflito)
public class EntidadeDuplicadaException extends NegocioException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public EntidadeDuplicadaException(String mensagem) {
        super(mensagem); // Chama o construtor da classe pai (NegocioException)
    }

}
