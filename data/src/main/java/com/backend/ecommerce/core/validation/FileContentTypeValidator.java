package com.backend.ecommerce.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

// Classe que valida o tipo de conteúdo de um arquivo enviado.
// Ela verifica se o tipo do arquivo está na lista de tipos permitidos.
public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    // Lista de tipos de conteúdo permitidos para os arquivos.
    private List<String> allowedContentTypes;

    /**
     * Esse método é chamado quando a anotação @FileContentType é usada.
     * A gente usa ele para pegar os tipos de conteúdo permitidos e configurar o validador.
     */
    @Override
    public void initialize(FileContentType constraint) {
        // Aqui pegamos os tipos de conteúdo permitidos da anotação e colocamos em uma lista.
        this.allowedContentTypes = Arrays.asList(constraint.allowed());
    }

    /**
     * Esse é o método de validação. Ele verifica se o arquivo é nulo (o que é permitido)
     * ou se o tipo de conteúdo do arquivo está na lista dos tipos permitidos.
     */
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        // Se o arquivo for nulo, ele é considerado válido.
        // Caso contrário, checa se o tipo de conteúdo está na lista permitida.
        return multipartFile == null
                || this.allowedContentTypes.contains(multipartFile.getContentType());
    }

}