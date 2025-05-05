package com.backend.ecommerce.api.exeptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    // Tipos de problemas que podem ser gerados durante o processamento das requisições.
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    // Título (nome legível) associado ao tipo de problema
    private String title;

    // URI completa do problema, usada na documentação ou como identificador externo
    private String uri;

    // Construtor que define o URI e o título para cada tipo de problema
    ProblemType(String path, String title) {
        // A URI é formada concatenando um domínio base com o caminho específico do problema
        this.uri = "https://e-commerce-linda-cosmeticos.com.br" + path;
        this.title = title; // Atribui o título correspondente
    }
}

