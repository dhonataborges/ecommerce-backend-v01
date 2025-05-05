package com.backend.ecommerce.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa os dados relacionados a um boleto de pagamento.
 * Essa classe é embutida em outra entidade, ou seja, não tem uma tabela própria,
 * mas seus campos serão persistidos dentro da tabela de outra entidade.
 */
@Getter
@Setter
@Embeddable // Indica que esta classe pode ser incorporada em outras entidades.
public class DadosBoleto {

    /**
     * Código do boleto gerado para o pagamento.
     * Este código será armazenado na coluna 'codigo_boleto' da tabela que
     * incorporar esta classe.
     */
    @Column(name = "codigo_boleto")
    private String codigoBoleto;
}