package com.backend.ecommerce.infrastructure.repository.service.storage;

import com.backend.ecommerce.core.storege.StorageProperties;
import com.backend.ecommerce.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Primary
@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties; // Propriedades de configuração para armazenamento local

    /**
     * Recupera um arquivo do armazenamento local com base no nome do arquivo.
     *
     * @param nomeArquivo nome do arquivo a ser recuperado.
     * @return InputStream do arquivo localizado.
     */
    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    /**
     * Armazena um novo arquivo (foto) no sistema de arquivos local.
     *
     * @param novaFoto objeto contendo os dados da foto a ser salva.
     */
    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeAquivo());

            // Copia o conteúdo do InputStream da foto para o caminho de destino
            FileCopyUtils.copy(
                    novaFoto.getInputStream(),
                    Files.newOutputStream(arquivoPath)
            );
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo! " +
                    "Verifique se o caminho do arquivo está correto em 'application.properties'.", e);
        }
    }

    /**
     * Remove um arquivo previamente armazenado no sistema de arquivos local.
     *
     * @param nomeArquivo nome do arquivo a ser removido.
     */
    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath); // Remove o arquivo apenas se ele existir
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    /**
     * Obtém o caminho completo do arquivo, com base no diretório de fotos configurado.
     *
     * @param nomeArquivo nome do arquivo.
     * @return Path representando o caminho absoluto do arquivo.
     */
    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos()
                .resolve(Path.of(nomeArquivo));
    }

}
