package com.backend.ecommerce.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Aqui a gente define onde essa anotação pode ser usada: em campos, métodos, parâmetros, etc.
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })

// Essa anotação vai estar disponível em tempo de execução (ou seja, na hora de rodar a aplicação).
@Retention(RUNTIME)

// Estamos dizendo que a classe MultiploValidator vai cuidar da lógica dessa anotação.
@Constraint(validatedBy = { MultiploValidator.class })
public @interface Multiplo {

    // Mensagem que aparece quando a validação falha.
    // Pode ser sobrescrita se quiser uma mensagem mais específica.
    String message() default "múltiplo inválido";

    // Coisa padrão do Bean Validation — serve para agrupar validações se precisar.
    Class<?>[] groups() default { };

    // Também é exigido pelo Bean Validation — geralmente a gente não usa.
    Class<? extends Payload>[] payload() default { };

    // Aqui é onde você define o número base da validação.
    // Ex: se colocar numero = 5, a validação vai checar se o valor é múltiplo de 5.
    int numero();
}
