package com.backend.ecommerce.core.storege;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    @Autowired
    private StorageProperties storageProperties;

   /* @Value("${ecommerce.storage.drive.credentials-path}")
    private String credentialsPath1;*/

    @Bean
    public Drive googleDrive() throws GeneralSecurityException, IOException {
        // Caminho do arquivo de credenciais JSON definido no application.propreties
        String credentialsPath = storageProperties.getDrive().getCredentialsPath();

        // 1️⃣ Carregar as credenciais do arquivo JSON
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialsPath))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // 2️⃣ Retornar o cliente do Google Drive
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(storageProperties.getDrive().getApplicationName()).build();
    }
}