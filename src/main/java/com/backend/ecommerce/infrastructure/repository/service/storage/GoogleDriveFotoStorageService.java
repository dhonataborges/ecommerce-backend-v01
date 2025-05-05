package com.backend.ecommerce.infrastructure.repository.service.storage;

import com.backend.ecommerce.core.storege.StorageProperties;
import com.backend.ecommerce.domain.service.FotoStorageService;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

//@Primary
//@Service
public class GoogleDriveFotoStorageService implements FotoStorageService {

    @Autowired
    private Drive driveService; // Cliente da API do Google Drive para executar operações de armazenamento

    @Autowired
    private StorageProperties storageProperties; // Propriedades customizadas de configuração da aplicação

    /**
     * Recupera um arquivo do Google Drive com base no nome do arquivo.
     *
     * @param nomeArquivo nome do arquivo a ser recuperado.
     * @return InputStream do arquivo encontrado.
     */
    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            String fileId = getFileId(nomeArquivo);
            if (fileId == null) {
                throw new RuntimeException("Arquivo não encontrado no Google Drive.");
            }
            return driveService.files().get(fileId).executeMediaAsInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao recuperar arquivo do Google Drive.", e);
        }
    }

    /**
     * Armazena uma nova foto no Google Drive.
     *
     * @param novaFoto objeto contendo dados da foto a ser armazenada.
     */
    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeAquivo());

            // Define os metadados do arquivo, como o nome e diretório (pasta) de destino
            File fileMetadata = new File();
            fileMetadata.setName(caminhoArquivo);

            // Cria ou localiza a pasta de destino no Google Drive
            String folderId = criarPasta(storageProperties.getDrive().getDiretorioFotos());

            if (folderId != null) {
                fileMetadata.setParents(Collections.singletonList(folderId));
            }

            // Define o conteúdo do arquivo (tipo MIME e stream de dados)
            InputStreamContent fileContent = new InputStreamContent(novaFoto.getContentType(), novaFoto.getInputStream());

            // Realiza o upload para o Google Drive
            File uploadedFile = driveService.files().create(fileMetadata, fileContent)
                    .setFields("id, webViewLink")
                    .execute();

            // Log para facilitar a verificação em ambiente de desenvolvimento
            System.out.println("Arquivo enviado com sucesso! ID do arquivo: " + uploadedFile.getId());

        } catch (Exception e) {
            // Tratamento genérico de erro ao armazenar o arquivo
            System.err.println("Erro ao enviar o arquivo para o Google Drive: " + e.getMessage());
            throw new StorageException("Não foi possível enviar o arquivo para o Google Drive.", e);
        }
    }

    /**
     * Remove um arquivo do Google Drive com base no nome do arquivo.
     *
     * @param nomeArquivo nome do arquivo a ser removido.
     */
    @Override
    public void remover(String nomeArquivo) {
        try {
            String fileId = getFileId(nomeArquivo);
            if (fileId != null) {
                driveService.files().delete(fileId).execute();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao remover arquivo do Google Drive.", e);
        }
    }

    /**
     * Busca o ID de um arquivo no Google Drive baseado em seu nome.
     *
     * @param nomeArquivo nome do arquivo.
     * @return ID do arquivo, ou null se não encontrado.
     */
    private String getFileId(String nomeArquivo) throws IOException {
        var result = driveService.files().list()
                .setQ(String.format("name='%s'", nomeArquivo))
                .setFields("files(id, name)")
                .execute();

        if (result.getFiles().isEmpty()) {
            return null;
        }

        return result.getFiles().get(0).getId();
    }

    /**
     * Cria uma pasta no Google Drive com o nome especificado, se ainda não existir.
     *
     * @param folderName nome da pasta.
     * @return ID da pasta criada ou encontrada.
     * @throws IOException em caso de falha de comunicação com o Google Drive.
     */
    private String criarPasta(String folderName) throws IOException {
        // Verifica se a pasta já existe
        FileList fileList = driveService.files().list()
                .setQ("mimeType = 'application/vnd.google-apps.folder' and name = '" + folderName + "'")
                .execute();

        // Se não existir, cria uma nova pasta
        if (fileList.getFiles().isEmpty()) {
            File folderMetadata = new File();
            folderMetadata.setName(folderName);
            folderMetadata.setMimeType("application/vnd.google-apps.folder");
            File folder = driveService.files().create(folderMetadata).execute();
            return folder.getId();
        } else {
            return fileList.getFiles().get(0).getId(); // Retorna o ID da pasta existente
        }
    }

    /**
     * Retorna o caminho lógico do arquivo.
     * Atualmente, apenas retorna o nome, mas pode ser customizado para incluir subdiretórios.
     *
     * @param nomeArquivo nome base do arquivo.
     * @return caminho completo do arquivo.
     */
    private String getCaminhoArquivo(String nomeArquivo) {
        return nomeArquivo;
    }

}
