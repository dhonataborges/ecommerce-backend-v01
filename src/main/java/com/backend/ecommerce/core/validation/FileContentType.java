package com.backend.ecommerce.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Essa anotação pode ser usada em vários lugares: campo, método, parâmetro, etc.
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })

// Diz que a anotação deve estar disponível em tempo de execução (essencial pra validação funcionar).
@Retention(RUNTIME)

// Aqui ligamos a anotação com a classe que vai validar ela (no caso, FileContentTypeValidator).
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {

    // Mensagem de erro padrão se o tipo do arquivo for inválido.
    // Dá pra sobrescrever direto onde for usar.
    String message() default "arquivo inválido";

    // Grupo de validação (normalmente a gente não usa isso em casos simples).
    Class<?>[] groups() default { };

    // Payload é mais usado pra carregar metadados. Também é obrigatório por padrão do Bean Validation.
    Class<? extends Payload>[] payload() default { };

    // Lista de tipos de conteúdo (MIME types) permitidos.
    // Exemplo: image/jpeg, image/png, application/pdf, etc.
    String[] allowed();
}
