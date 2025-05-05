package com.backend.ecommerce.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

// Classe que implementa a lógica de validação personalizada para a anotação @Multiplo.
// Ela valida se um número é múltiplo de um valor específico.
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    // Armazena o número base para o qual o valor deve ser múltiplo.
    private int numeroMultiplo;

    /**
     * Método chamado na inicialização da validação.
     * Aqui, recuperamos o número definido na anotação @Multiplo(numero = X).
     *
     * @param constraintAnnotation a instância da anotação contendo o valor de configuração.
     */
    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    /**
     * Realiza a validação de fato.
     * Verifica se o valor fornecido é múltiplo de `numeroMultiplo`.
     *
     * @param value   o valor a ser validado
     * @param context o contexto da validação (pode ser usado para mensagens customizadas)
     * @return true se válido ou se for nulo (por padrão, anotações não consideram null inválido)
     */
    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valido = true;

        // Se o valor não for nulo, aplica a regra de validação.
        if (value != null) {
            // Converte o número para BigDecimal para garantir precisão na divisão.
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);

            // Calcula o resto da divisão
            var resto = valorDecimal.remainder(multiploDecimal);

            // Verifica se o resto é zero (ou seja, se é múltiplo)
            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }

}