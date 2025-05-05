package com.backend.ecommerce.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

// Classe que valida o tamanho do arquivo enviado.
// Ela verifica se o arquivo não excede o tamanho máximo definido.
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    // Aqui estamos armazenando o tamanho máximo que o arquivo pode ter.
    private DataSize maxSize;

    /**
     * Esse método é chamado quando a anotação @FileSize é usada.
     * A gente usa para pegar o valor do tamanho máximo e fazer a configuração inicial.
     */
    @Override
    public void initialize(FileSize constraintAnnotation) {
        // Pega o valor definido na anotação e converte para um tipo de dados que conseguimos comparar (DataSize).
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    /**
     * Esse é o método que faz a validação.
     * Ele verifica se o arquivo é nulo (o que é permitido) ou se o tamanho dele não ultrapassa o tamanho máximo.
     */
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        // Se o arquivo for nulo, a validação passa. Caso contrário, compara o tamanho do arquivo.
        return value == null || value.getSize() <= this.maxSize.toBytes();
    }

}