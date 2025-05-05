package com.backend.ecommerce.domain.exception.entidadeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.backend.ecommerce.domain.exception.NegocioException;

/**
 * Exceção lançada quando uma entidade está em uso e não pode ser modificada ou excluída.
 * Esta exceção estende 'NegocioException' e é usada para indicar que uma operação
 * não pode ser realizada porque a entidade está sendo utilizada em outro contexto.
 * <p>
 * A anotação '@ResponseStatus' define que, quando essa exceção for lançada, o código de
 * status HTTP retornado será 409 (CONFLICT), indicando que a operação não pôde ser completada
 * devido a um conflito com o estado atual da entidade.
 */
@ResponseStatus(value = HttpStatus.CONFLICT) // Define o código de status HTTP como 409 (Conflito)
public class EntidadeEmUsoException extends NegocioException {

    private static final long serialVersionUID = 1L; // Identificador de versão para serialização

    /**
     * Construtor que permite fornecer uma mensagem personalizada para a exceção.
     *
     * @param mensagem Mensagem explicativa sobre o erro ocorrido.
     */
    public EntidadeEmUsoException(String mensagem) {
        super(mensagem); // Chama o construtor da classe pai (NegocioException)
    }

}
