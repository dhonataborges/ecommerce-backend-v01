package com.backend.ecommerce.domain.service;


import lombok.Builder;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    @Builder
    @Getter
    class NovaFoto {

        private String nomeAquivo;
        private String contentType;
        private InputStream inputStream;

    }

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    @Transactional
    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

}