package com.backend.ecommerce.domain.service;


import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

/**
 * Interface que define operações para o serviço de armazenamento de fotos.
 * Permite armazenar, recuperar e remover arquivos, além de gerar nomes únicos.
 */
public interface FotoStorageService {

    /**
     * Representa os dados necessários para armazenar uma nova foto.
     */
    @Builder
    @Getter
    class NovaFoto {
        private String nomeAquivo;       // Nome do arquivo a ser salvo
        private String contentType;      // Tipo MIME do conteúdo (ex: image/jpeg)
        private InputStream inputStream; // Dados binários da imagem
    }

    /**
     * Recupera uma foto a partir de seu nome de arquivo.
     *
     * @param nomeArquivo Nome do arquivo a ser recuperado.
     * @return Stream com o conteúdo da imagem.
     */
    InputStream recuperar(String nomeArquivo);

    /**
     * Armazena uma nova foto.
     *
     * @param novaFoto Objeto contendo os dados da nova foto.
     */
    void armazenar(NovaFoto novaFoto);

    /**
     * Remove uma foto do armazenamento com base no nome do arquivo.
     *
     * @param nomeArquivo Nome do arquivo a ser removido.
     */
    void remover(String nomeArquivo);

    /**
     * Substitui uma foto existente por uma nova.
     * Remove a antiga somente se o nome for fornecido.
     *
     * @param nomeArquivoAntigo Nome do arquivo anterior (pode ser null).
     * @param novaFoto Dados da nova foto a ser armazenada.
     */
    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    /**
     * Gera um nome único para o arquivo com base em UUID e no nome original.
     *
     * @param nomeOriginal Nome original do arquivo.
     * @return Nome único gerado.
     */
    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }
}