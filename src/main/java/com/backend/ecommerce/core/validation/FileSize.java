package com.backend.ecommerce.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Definindo a anotação personalizada @FileSize
// Ela pode ser usada em campos, métodos, parâmetros, etc.
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})

// Faz com que a anotação esteja disponível em tempo de execução.
@Retention(RUNTIME)

// Estamos dizendo que a lógica de validação será feita pela classe FileSizeValidator.
@Constraint(validatedBy = {FileSizeValidator.class})
public @interface FileSize {

    // A mensagem padrão que vai ser exibida quando a validação falhar.
    // Pode ser personalizada diretamente na anotação.
    String message() default "tamanho do arquivo inválido";

    // Permite agrupar as validações, mas na maioria dos casos não usamos isso.
    Class<?>[] groups() default { };

    // Serve para carregar dados adicionais para a validação (geralmente não usado diretamente).
    Class<? extends Payload>[] payload() default { };

    // Aqui definimos o tamanho máximo do arquivo. O valor é passado como uma string, como "2MB", "500KB", etc.
    String max();
}
