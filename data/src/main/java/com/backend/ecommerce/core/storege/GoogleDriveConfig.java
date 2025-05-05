package com.backend.ecommerce.core.storege;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

//@Configuration
// Se você quiser que essa classe seja reconhecida como uma classe de configuração pelo Spring,
// descomente essa anotação. Assim, o Spring vai conseguir registrar o Bean do Google Drive aqui.

public class GoogleDriveConfig {

    @Autowired
    private StorageProperties storageProperties;
    // Aqui a gente injeta a classe que contém os dados de configuração (como o caminho do JSON e nome da app),
    // que você mapeou lá com @ConfigurationProperties(prefix = "ecommerce.storage").

    /*
    @Value("${ecommerce.storage.drive.credentials-path}")
    private String credentialsPath1;
    */
    // Isso aqui era uma forma alternativa de pegar o caminho direto pelo @Value,
    // mas como você já tá usando o `StorageProperties`, não precisa mais.

    // @Bean
    // Essa anotação transforma o método abaixo em um Bean gerenciado pelo Spring.
    // Ou seja, você pode injetar `Drive` em qualquer lugar. Basta descomentar.
    public Drive googleDrive() throws GeneralSecurityException, IOException {

        // Caminho do JSON de credenciais (vem do application.properties ou .yml)
        String credentialsPath = storageProperties.getDrive().getCredentialsPath();

        // Carrega o arquivo de credenciais e configura o escopo de acesso para o Google Drive
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialsPath)) // Lê o JSON
                .createScoped(Collections.singleton(DriveScopes.DRIVE)); // Diz que vamos acessar o Drive

        // Cria o client do Google Drive com essas credenciais
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),     // Usa transporte HTTP seguro do Google
                JacksonFactory.getDefaultInstance(),              // JSON mapper padrão do Google
                new HttpCredentialsAdapter(credentials)           // Passa as credenciais
        ).setApplicationName(storageProperties.getDrive().getApplicationName()) // Nome da sua app (aparece no painel do Google)
                .build();
    }
}
