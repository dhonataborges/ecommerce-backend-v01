package com.backend.ecommerce.core.storege;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ecommerce.storage") // Mapeia as propriedades com o prefixo "ecommerce.storage"
public class StorageProperties {

    private Drive drive = new Drive();

    private Local local = new Local();

    // Classe para propriedades do armazenamento Local
    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;
    }

    // Classe para propriedades do Google Drive
    @Getter
    @Setter
    public class Drive {
        private String credentialsPath;  // Caminho do arquivo JSON de credenciais
        private String applicationName;  // Nome da aplicação
        private String diretorioFotos;   // Diretório de fotos no Google Drive
    }

}