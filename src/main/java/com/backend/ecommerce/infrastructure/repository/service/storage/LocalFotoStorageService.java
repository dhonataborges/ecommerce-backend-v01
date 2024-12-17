package com.backend.ecommerce.infrastructure.repository.service.storage;

import com.backend.ecommerce.core.storege.StorageProperties;
import com.backend.ecommerce.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    // Método responsável por recuperar o arquivo a partir do diretório local
    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            // Chama o método 'getArquivoPath' para obter o caminho do arquivo no sistema de arquivos local
            Path arquivoPath = getArquivoPath(nomeArquivo);

            // Retorna o InputStream do arquivo encontrado
            return Files.newInputStream(arquivoPath);
        } catch (Exception e) {
            // Caso ocorra algum erro, uma exceção customizada 'StorageException' é lançada
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    // Método responsável por armazenar um novo arquivo (representado por 'NovaFoto') no sistema de arquivos local
    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            // Chama o método 'getArquivoPath' para obter o caminho completo do arquivo
            Path arquivoPath = getArquivoPath(novaFoto.getNomeAquivo());

            // Utiliza 'FileCopyUtils' para copiar os dados do InputStream da foto para o caminho destino
            FileCopyUtils.copy(novaFoto.getInputStream(),
                    Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            // Se ocorrer algum erro, lança uma exceção customizada 'StorageException' com mensagem explicativa
            throw new StorageException("Não foi possível armazenar arquivo!" +
                    " Verifique se o caminho do arquivo está correto. Em 'application.properties'. ", e);
        }
    }

    // Método responsável por excluir um arquivo a partir do nome do arquivo no diretório local
    @Override
    public void remover(String nomeArquivo) {
        try {
            // Chama o método 'getArquivoPath' para obter o caminho do arquivo a ser excluído
            Path arquivoPath = getArquivoPath(nomeArquivo);

            // Tenta excluir o arquivo, caso exista
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            // Se ocorrer algum erro ao excluir o arquivo, lança uma exceção customizada 'StorageException'
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    // Método auxiliar para obter o caminho completo do arquivo, resolvendo o nome do arquivo com o diretório de fotos configurado
    private Path getArquivoPath(String nomeArquivo) {
        // Utiliza 'resolve' para obter o caminho completo, concatenando o diretório base com o nome do arquivo
        return storageProperties.getLocal().getDiretorioFotos()
                .resolve(Path.of(nomeArquivo));
    }

}