package com.backend.ecommerce.api.exeptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL) // Ignora campos nulos na serialização JSON
@Getter // Gera os métodos getters automaticamente
@Builder // Permite a construção da classe utilizando o padrão de builder
public class Problem {

    // Código de status HTTP do erro
    private Integer status;

    // Data e hora em que o problema ocorreu (timestamp)
    private OffsetDateTime timestamp;

    // Tipo de erro, geralmente será uma URL associada ao erro
    private String type;

    // Título do erro (uma descrição breve)
    private String title;

    // Detalhes do erro, uma explicação mais detalhada sobre o que ocorreu
    private String detail;

    // Mensagem de erro a ser exibida ao usuário final
    private String userMessage;

    // Lista de objetos de erro (como campos ou parâmetros que causaram o erro)
    private List<Object> objects;

    // Classe interna representando um objeto de erro (geralmente um campo ou parâmetro que gerou o erro)
    @Getter
    @Builder
    public static class Object {

        // Nome do campo ou parâmetro que causou o erro
        private String name;

        // Mensagem de erro associada ao campo ou parâmetro específico
        private String userMessage;

    }
}
