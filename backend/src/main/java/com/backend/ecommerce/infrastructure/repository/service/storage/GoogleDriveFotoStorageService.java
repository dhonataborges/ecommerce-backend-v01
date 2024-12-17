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

@Primary
@Service
public class GoogleDriveFotoStorageService implements FotoStorageService {

    @Autowired
    private Drive driveService;

    @Autowired
    private StorageProperties storageProperties;

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

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeAquivo());

            // 1️⃣ Metadados do arquivo
            File fileMetadata = new File();
            fileMetadata.setName(caminhoArquivo);

            // 2️⃣ Definindo a pasta de destino no Google Drive
            String folderId = criarPasta(storageProperties.getDrive().getDiretorioFotos());

            if (folderId != null) {
                fileMetadata.setParents(Collections.singletonList(folderId));
            }

            // 3️⃣ Conteúdo do arquivo
            InputStreamContent fileContent = new InputStreamContent(novaFoto.getContentType(), novaFoto.getInputStream());

            // 4️⃣ Upload do arquivo
            File uploadedFile = driveService.files().create(fileMetadata, fileContent)
                    .setFields("id, webViewLink")
                    .execute();

            // Log de sucesso
            System.out.println("Arquivo enviado com sucesso! ID do arquivo: " + uploadedFile.getId());

        } catch (Exception e) {
            // Melhorando o tratamento de exceções
            System.err.println("Erro ao enviar o arquivo para o Google Drive: " + e.getMessage());
            throw new StorageException("Não foi possível enviar o arquivo para o Google Drive.", e);
        }
    }


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

    /*private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getDrive().getDiretorioFotos(), nomeArquivo);
    }*/

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
     * Verifica se a pasta existe no Google Drive. Se não existir, cria uma nova.
     * @param folderName O nome da pasta a ser verificada/criada.
     * @return O ID da pasta.
     * @throws IOException Se houver erro na comunicação com o Google Drive.
     */
    private String criarPasta(String folderName) throws IOException {
        // Verifica se a pasta existe
        FileList fileList = driveService.files().list()
                .setQ("mimeType = 'application/vnd.google-apps.folder' and name = '" + folderName + "'")
                .execute();

        // Se a pasta não existir, cria uma nova
        if (fileList.getFiles().isEmpty()) {
            File folderMetadata = new File();
            folderMetadata.setName(folderName);
            folderMetadata.setMimeType("application/vnd.google-apps.folder");
            File folder = driveService.files().create(folderMetadata).execute();
            return folder.getId();
        } else {
            // Se a pasta já existir, retorna o ID da pasta
            return fileList.getFiles().get(0).getId();
        }
    }

    /**
     * Método para obter o caminho ou nome do arquivo, pode ser implementado conforme sua lógica.
     * @param nomeArquivo O nome original do arquivo.
     * @return O caminho completo ou nome do arquivo para o Google Drive.
     */
    private String getCaminhoArquivo(String nomeArquivo) {
        // Aqui você pode implementar a lógica para gerar um caminho para o arquivo,
        // como concatenar o nome da pasta com o nome do arquivo ou algo do tipo.
        return nomeArquivo;  // Exemplo simples, retornando o nome do arquivo diretamente.
    }

}
